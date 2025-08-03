package wasteanalytics;

import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WasteAnalyticsClient {
    public static void main(String[] args) throws Exception {
        String service_type = "_grpc._tcp.local.";
        String service_name = "WasteAnalytics";

        // Discover the service using JmDNS
        JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
        ServiceInfo serviceInfo = jmdns.getServiceInfo(service_type, service_name, 5000);

        if (serviceInfo == null) {
            System.out.println("Service '" + service_name + "' not found!");
            return;
        }

        String host = serviceInfo.getHostAddresses()[0];
        int port = serviceInfo.getPort();

        // Create a gRPC channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        // Create and attach metadata for authentication
        Metadata metadata = new Metadata();
        Metadata.Key<String> authKey = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(authKey, "secreto123");  // Expected token by server

        // Create base stub without timeout (yet)
        WasteAnalyticsServiceGrpc.WasteAnalyticsServiceStub stub =
                WasteAnalyticsServiceGrpc.newStub(channel);
        stub = MetadataUtils.attachHeaders(stub, metadata); // Attach auth token

        Scanner scanner = new Scanner(System.in);
        List<WasteRecord> records = new ArrayList<>();

        System.out.println("Enter waste records. Type 'done' to finish.");

        while (true) {
            // Validate material
            String material = "";
            while (true) {
                System.out.print("\nMaterial (plastic/cardboard) or 'done' to finish: ");
                material = scanner.nextLine().trim().toLowerCase();

                if (material.equals("done")) {
                    break;
                }

                if (material.equals("plastic") || material.equals("cardboard")) {
                    break;
                }

                System.out.println("\u274C Invalid material. Please enter 'plastic' or 'cardboard'.");
            }

            if (material.equals("done")) {
                break;
            }

            // Validate condition
            String condition = "";
            while (true) {
                System.out.print("Condition (good/damaged): ");
                condition = scanner.nextLine().trim().toLowerCase();

                if (condition.equals("good") || condition.equals("damaged")) break;

                System.out.println("\u274C Invalid condition. Please enter 'good' or 'damaged'.");
            }

            // Validate size
            String size = "";
            while (true) {
                System.out.print("Size (small/large): ");
                size = scanner.nextLine().trim().toLowerCase();

                if (size.equals("small") || size.equals("large")) break;

                System.out.println("\u274C Invalid size. Please enter 'small' or 'large'.");
            }

            // Validate date
            String date = "";
            while (true) {
                System.out.print("Date (format YYYY-MM-DD): ");
                date = scanner.nextLine().trim();

                if (date.matches("\\d{4}-\\d{2}-\\d{2}")) break;

                System.out.println("\u274C Invalid date. Use format YYYY-MM-DD.");
            }

            // Create and store the record
            WasteRecord record = WasteRecord.newBuilder()
                    .setMaterial(material)
                    .setCondition(condition)
                    .setSize(size)
                    .setDate(date)
                    .build();

            records.add(record);
            System.out.println("\u2714 Record added.");
        }

        if (records.isEmpty()) {
            System.out.println("No records to send.");
            channel.shutdown();
            return;
        }

        // Prepare for receiving report
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<DailyReport> responseObserver = new StreamObserver<DailyReport>() {
            @Override
            public void onNext(DailyReport report) {
                System.out.println("\n=== Daily Summary ===");
                System.out.println("Total Waste: " + report.getDailySummary().getTotalWaste());
                System.out.println("Eligible for Sale: " + report.getDailySummary().getEligibleForSale());
                System.out.println("Recyclable: " + report.getDailySummary().getRecyclable());
                System.out.println("Non-Recoverable: " + report.getDailySummary().getNonRecoverable());

                System.out.println("\n=== Recycling Benefits ===");
                System.out.println("Reused by Soltex: " + report.getRecyclingBenefits().getReusedBySoltex());
                System.out.println("Recycled Cones: " + report.getRecyclingBenefits().getRecycledCones());
                System.out.println("Estimated Savings: " + report.getRecyclingBenefits().getEstimatedSavings());
                System.out.println("Waste Diverted: " + report.getRecyclingBenefits().getWasteDivertedFromLandfill());
                System.out.println("Tip: " + report.getRecyclingBenefits().getTip());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("\n\u274C Error from server:");
                if (t instanceof StatusRuntimeException) {
                    StatusRuntimeException ex = (StatusRuntimeException) t;
                    System.err.println("Status: " + ex.getStatus());
                    System.err.println("Description: " + ex.getStatus().getDescription());
                } else {
                    t.printStackTrace();
                }
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\u2705 Report completed.");
                latch.countDown();
            }
        };

        // ✅ APLICA DEADLINE SOLO AHORA:
        stub = stub.withDeadlineAfter(30, TimeUnit.SECONDS);

        // Send all records to server
        StreamObserver<WasteRecord> requestObserver = stub.submitDailyWaste(responseObserver);
        for (WasteRecord r : records) {
            requestObserver.onNext(r);
        }
        requestObserver.onCompleted();

        // Wait for server response
        latch.await(10, TimeUnit.SECONDS);
        channel.shutdown();
    }
}
