package wasteanalytics; // This file belongs to the 'wasteanalytics' package.

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

        String service_type = "_grpc._tcp.local."; // The type of service to search for.
        String service_name = "WasteAnalytics";    // The name of the service.

        // Discover the service on the local network using JmDNS
        JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
        ServiceInfo serviceInfo = jmdns.getServiceInfo(service_type, service_name, 5000); // wait up to 5 seconds

        if (serviceInfo == null) {
            System.out.println("Service '" + service_name + "' not found.");
            return;
        }

        // Get the IP address and port of the service
        String host = serviceInfo.getHostAddresses()[0];
        int port = serviceInfo.getPort();

        // Create a gRPC communication channel with the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        // Create metadata for authentication
        Metadata metadata = new Metadata();
        Metadata.Key<String> authKey = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(authKey, "secreto123");  // Token expected by the server

        // Create a base stub and attach the metadata
        WasteAnalyticsServiceGrpc.WasteAnalyticsServiceStub stub =
                WasteAnalyticsServiceGrpc.newStub(channel);
        stub = MetadataUtils.attachHeaders(stub, metadata);

        // Use Scanner to get user input
        Scanner scanner = new Scanner(System.in);
        List<WasteRecord> records = new ArrayList<>();

        System.out.println("Enter waste records. Type 'done' to finish.");

        // Collect records from user
        while (true) {
            String material = "";
            while (true) {
                System.out.print("\nMaterial (plastic/cardboard) or 'done' to finish: ");
                material = scanner.nextLine().trim().toLowerCase();

                if (material.equals("done")) break;
                if (material.equals("plastic") || material.equals("cardboard")) break;

                System.out.println("Invalid material. Please enter 'plastic' or 'cardboard'.");
            }

            if (material.equals("done")) break;

            String condition = "";
            while (true) {
                System.out.print("Condition (good/damaged): ");
                condition = scanner.nextLine().trim().toLowerCase();
                if (condition.equals("good") || condition.equals("damaged")) break;

                System.out.println("Invalid condition. Please enter 'good' or 'damaged'.");
            }

            String size = "";
            while (true) {
                System.out.print("Size (small/large): ");
                size = scanner.nextLine().trim().toLowerCase();
                if (size.equals("small") || size.equals("large")) break;

                System.out.println("Invalid size. Please enter 'small' or 'large'.");
            }

            String date = "";
            while (true) {
                System.out.print("Date (format YYYY-MM-DD): ");
                date = scanner.nextLine().trim();
                if (date.matches("\\d{4}-\\d{2}-\\d{2}")) break;

                System.out.println("Invalid date. Use format YYYY-MM-DD.");
            }

            // Build the record and store it
            WasteRecord record = WasteRecord.newBuilder()
                    .setMaterial(material)
                    .setCondition(condition)
                    .setSize(size)
                    .setDate(date)
                    .build();

            records.add(record);
            System.out.println("Record added.");
        }

        // If no data was entered, stop
        if (records.isEmpty()) {
            System.out.println("No records to send.");
            channel.shutdown();
            return;
        }

        // Create a latch to wait for server response
        CountDownLatch latch = new CountDownLatch(1);

        // Handle server responses
        StreamObserver<DailyReport> responseObserver = new StreamObserver<DailyReport>() {
            @Override
            public void onNext(DailyReport report) {
                // Print summary from the report
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
                System.err.println("\nError from server:");
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
                System.out.println("\nReport completed.");
                latch.countDown();
            }
        };

        // Set a 30-second deadline for the response
        stub = stub.withDeadlineAfter(30, TimeUnit.SECONDS);

        // Send all records to the server
        StreamObserver<WasteRecord> requestObserver = stub.submitDailyWaste(responseObserver);
        for (WasteRecord r : records) {
            requestObserver.onNext(r);
        }
        requestObserver.onCompleted();

        // Wait for the response or timeout
        latch.await(10, TimeUnit.SECONDS);
        channel.shutdown(); // Close the connection
    }
}
