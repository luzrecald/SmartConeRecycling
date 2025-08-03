// WasteAnalyticsServer.java
package wasteanalytics;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WasteAnalyticsServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 5056;

        // Start gRPC server and register the service with an authentication interceptor
        Server server = ServerBuilder.forPort(port)
                .addService(ServerInterceptors.intercept(new WasteAnalyticsServiceImpl(), new AuthInterceptor()))
                .build();

        // Optional: Service registration via JmDNS (you can comment this if not using JmDNS)
        ServiceRegistration.register("WasteAnalytics", port);

        System.out.println("Starting WasteAnalytics server on port " + port + "...");
        server.start();
        System.out.println("Server started.");
        server.awaitTermination();
    }

    // Implementation of the WasteAnalytics gRPC service
    static class WasteAnalyticsServiceImpl extends WasteAnalyticsServiceGrpc.WasteAnalyticsServiceImplBase {
        @Override
        public StreamObserver<WasteRecord> submitDailyWaste(StreamObserver<DailyReport> responseObserver) {
            List<WasteRecord> records = new ArrayList<>();

            return new StreamObserver<WasteRecord>() {
                @Override
                public void onNext(WasteRecord record) {
                    // Input validation
                    if (record.getMaterial().isEmpty() || record.getCondition().isEmpty()) {
                        responseObserver.onError(
                                Status.INVALID_ARGUMENT
                                        .withDescription("Material and condition cannot be empty.")
                                        .asRuntimeException()
                        );
                        return;
                    }

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
                        DailySummary.Builder summary = DailySummary.newBuilder();
                        RecyclingBenefits.Builder benefits = RecyclingBenefits.newBuilder();

                        int recyclable = 0, eligibleForSale = 0, nonRecoverable = 0;

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

                        summary.setEligibleForSale(eligibleForSale)
                               .setRecyclable(recyclable)
                               .setNonRecoverable(nonRecoverable);

                        benefits.setReusedBySoltex(eligibleForSale / 2)
                                .setRecycledCones(recyclable)
                                .setEstimatedSavings("14000 gs")
                                .setWasteDivertedFromLandfill("75%")
                                .setTip("Keep materials clean and sort them correctly!");

                        DailyReport report = DailyReport.newBuilder()
                                .setDailySummary(summary)
                                .setRecyclingBenefits(benefits)
                                .build();

                        responseObserver.onNext(report);
                        responseObserver.onCompleted();

                    } catch (Exception e) {
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

    // Interceptor to enforce basic token authentication using gRPC metadata
    static class AuthInterceptor implements ServerInterceptor {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> call,
                Metadata headers,
                ServerCallHandler<ReqT, RespT> next) {

            String token = headers.get(Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER));

            if (token == null || !token.equals("secreto123")) {
                call.close(Status.PERMISSION_DENIED.withDescription("Missing or invalid auth-token."), new Metadata());
                return new ServerCall.Listener<ReqT>() {};
            }

            return next.startCall(call, headers);
        }
    }
}
