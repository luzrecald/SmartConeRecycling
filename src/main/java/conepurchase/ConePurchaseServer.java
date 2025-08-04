package conepurchase;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import conepurchase.ConePurchaseServiceGrpc.ConePurchaseServiceImplBase;
import conepurchase.ConeSaleRequest;
import conepurchase.ConeSaleResponse;

public class ConePurchaseServer extends ConePurchaseServiceImplBase {

    private static final Logger logger = Logger.getLogger(ConePurchaseServer.class.getName());

    public static void main(String[] args) {
        ConePurchaseServer serviceImpl = new ConePurchaseServer();
        int port = 50054;

        try {
            // Create and start the gRPC server on the given port
            Server server = ServerBuilder.forPort(port)
                    // Add an authentication check for incoming requests
                    .addService(ServerInterceptors.intercept(serviceImpl, new AuthInterceptor()))
                    .build()
                    .start();

            logger.info("ConePurchaseServer started on port " + port);
            System.out.println("ConePurchaseServer started on port " + port);

            // Register this service on the local network using mDNS
            ConePurchaseServiceRegistration.getInstance()
                    .registerService("_grpc._tcp.local.", "ConePurchase", port, "gRPC Cone Purchase Service");

            // Keep the server running
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestConeSale(ConeSaleRequest request, StreamObserver<ConeSaleResponse> responseObserver) {
        try {
            System.out.println("Received ConeSaleRequest");

            int quantity = request.getQuantity();
            String location = request.getLocation().trim();

            // Check if the quantity is valid (must be more than 0)
            if (quantity <= 0) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Quantity must be greater than zero")
                        .asRuntimeException());
                return;
            }

            // Check if the location is valid (can't be empty)
            if (location.isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Location cannot be empty")
                        .asRuntimeException());
                return;
            }

            // If the order is small (less than 100 cones), only respond with info about pickup not being available
            if (quantity < 100) {
                ConeSaleResponse pickupNotAvailable = ConeSaleResponse.newBuilder()
                        .setMessageType("pickupOption")
                        .setMessage("Pickup service only available for orders of 100+ cones.")
                        .build();
                responseObserver.onNext(pickupNotAvailable);
                responseObserver.onCompleted();
                return;
            }

            // For large orders (100 or more), respond with full details: price, delivery, and pickup
            responseObserver.onNext(ConeSaleResponse.newBuilder()
                    .setMessageType("price")
                    .setMessage("Total: $" + (quantity * 15))
                    .build());

            responseObserver.onNext(ConeSaleResponse.newBuilder()
                    .setMessageType("logisticStatus")
                    .setMessage("Estimated delivery in 48h to " + location)
                    .build());

            responseObserver.onNext(ConeSaleResponse.newBuilder()
                    .setMessageType("pickupOption")
                    .setMessage("Pickup service scheduled at your location")
                    .setDay("Friday")
                    .setTimeslot("10:00 AM - 1:00 PM")
                    .build());

            // End the stream of responses
            responseObserver.onCompleted();

        } catch (Exception e) {
            // If any unexpected error happens, return a server error
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Server error: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
