// WasteAnalyticsServer.java
package wasteanalytics;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WasteAnalyticsServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 5056; // Port where the server will listen

        // Create and start the gRPC server, adding a service and an authentication check
        Server server = ServerBuilder.forPort(port)
                .addService(ServerInterceptors.intercept(new WasteAnalyticsServiceImpl(), new AuthInterceptor()))
                .build();

        // Register the service on the local network (optional)
        ServiceRegistration.register("WasteAnalytics", port);

        System.out.println("Starting WasteAnalytics server on port " + port + "...");
        server.start(); // Start the server
        System.out.println("Server started.");
        server.awaitTermination(); // Keep server running
    }

    // This class defines what the server does when it receives waste records
    static class WasteAnalyticsServiceImpl extends WasteAnalyticsServiceGrpc.WasteAnalyticsServiceImplBase {
        @Override
        public StreamObserver<WasteRecord> submitDailyWaste(StreamObserver<DailyReport> responseObserver) {
            List<WasteRecord> records = new ArrayList<>();

            // Handle each message (record) sent by the client
            return new StreamObserver<WasteRecord>() {
                @Override
                public void onNext(WasteRecord record) {
                    // Check for missing required fields
                    if (record.getMaterial().isEmpty() || record.getCondition().isEmpty()) {
                        responseObserver.onError(
                                Status.INVALID_ARGUMENT
                                        .withDescription("Material and condition cannot be empty.")
                                        .asRuntimeException()
                        );
                        return;
                    }

                    // Add the valid record to the list
                    records.add(record);
                    System.out.println("Received: " + record);
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Client error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    try {
                        // Create builders for the summary and benefits sections
                        DailySummary.Builder summary = DailySummary.newBuilder();
                        RecyclingBenefits.Builder benefits = RecyclingBenefits.newBuilder();

                        int recyclable = 0;
                        int eligibleForSale = 0;
                        int nonRecoverable = 0;

                        // Go through all the records and analyze them
                        for (WasteRecord r : records) {
                            summary.setTotalWaste(summary.getTotalWaste() + 1);

                            boolean isGood = r.getCondition().equalsIgnoreCase("good");
                            boolean isRecyclable = r.getMaterial().equalsIgnoreCase("plastic")
                                    || r.getMaterial().equalsIgnoreCase("cardboard");

                            if (isGood && isRecyclable) {
                                eligibleForSale++;
                                recyclable++;
                            } else {
                                nonRecoverable++;
                            }
                        }

                        // Set the final values in the summary
                        summary.setEligibleForSale(eligibleForSale)
                               .setRecyclable(recyclable)
                               .setNonRecoverable(nonRecoverable);

                        // Set estimated benefits (these values are fixed or based on the counts)
                        benefits.setReusedBySoltex(eligibleForSale / 2)
                                .setRecycledCones(recyclable)
                                .setEstimatedSavings("14$")
                                .setWasteDivertedFromLandfill("75%")
                                .setTip("Keep materials clean and sort them correctly!");

                        // Create the full report to send to the client
                        DailyReport report = DailyReport.newBuilder()
                                .setDailySummary(summary)
                                .setRecyclingBenefits(benefits)
                                .build();

                        // Send the report
                        responseObserver.onNext(report);
                        responseObserver.onCompleted();

                    } catch (Exception e) {
                        // Handle any internal error that might happen during report creation
                        responseObserver.onError(
                                Status.INTERNAL
                                        .withDescription("Error while generating report: " + e.getMessage())
                                        .asRuntimeException()
                        );
                    }
                }
            };
        }
    }

    // This part checks if the client sent a valid authentication token
    static class AuthInterceptor implements ServerInterceptor {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> call,
                Metadata headers,
                ServerCallHandler<ReqT, RespT> next) {

            // Read the auth-token from the request headers
            String token = headers.get(Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER));

            // Reject the request if the token is missing or incorrect
            if (token == null || !token.equals("secreto123")) {
                call.close(Status.PERMISSION_DENIED.withDescription("Missing or invalid auth-token."), new Metadata());
                return new ServerCall.Listener<ReqT>() {};
            }

            // Allow the request to continue if the token is correct
            return next.startCall(call, headers);
        }
    }
}
