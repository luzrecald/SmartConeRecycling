package conepurchase;

import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import conepurchase.ConePurchaseServiceGrpc;
import conepurchase.ConeSaleRequest;
import conepurchase.ConeSaleResponse;

public class ConePurchaseClient {
    private static final Logger logger = Logger.getLogger(ConePurchaseClient.class.getName());

    public static void main(String[] args) throws Exception {
        // Try to find the service that sells cones
        ConePurchaseServiceDiscovery.ServiceDetails service = ConePurchaseServiceDiscovery.discover();

        // If the service wasn't found, show an error and stop the program
        if (service == null) {
            System.err.println("Service 'ConePurchase' not found!");
            return;
        }

        // Show where the service was found
        System.out.println("Service found at: " + service.host + ":" + service.port);

        // Create a channel to connect with the server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(service.host, service.port)
                .usePlaintext()
                .build();

        // Add authentication information (like a password) to the request
        Metadata metadata = new Metadata();
        Metadata.Key<String> AUTH_TOKEN_KEY = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(AUTH_TOKEN_KEY, "securetoken123");

        // Create a gRPC stub (client) and attach the authentication info
        ConePurchaseServiceGrpc.ConePurchaseServiceStub rawStub =
                ConePurchaseServiceGrpc.newStub(channel);
        rawStub = MetadataUtils.attachHeaders(rawStub, metadata);

        Scanner scanner = new Scanner(System.in);

        // Ask the user for the number of cones, and keep asking until it's a positive number
        int quantity = -1;
        while (quantity <= 0) {
            System.out.print("Enter quantity of cones to sell (> 0): ");
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }

        // If the order is large (100 or more), ask for a location to pick it up
        String location = "";
        if (quantity >= 100) {
            do {
                System.out.print("Enter your location (required for pickup): ");
                location = scanner.nextLine().trim();
                if (location.isEmpty()) {
                    System.out.println("Location cannot be empty for orders of 100+ cones.");
                }
            } while (location.isEmpty());
        } else {
            location = "N/A"; // Just put something so the request is complete
        }

        // Create the request with the data entered
        ConeSaleRequest request = ConeSaleRequest.newBuilder()
                .setQuantity(quantity)
                .setLocation(location)
                .build();

        // Set a time limit for how long we wait for the server to answer
        ConePurchaseServiceGrpc.ConePurchaseServiceStub asyncStub =
                rawStub.withDeadlineAfter(3, TimeUnit.SECONDS);

        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("\nSending request to server...\n");

        // Send the request and handle the server's response
        asyncStub.requestConeSale(request, new StreamObserver<ConeSaleResponse>() {
            @Override
            public void onNext(ConeSaleResponse response) {
                // Print the main message from the server
                System.out.println("[" + response.getMessageType() + "] " + response.getMessage());

                // If the server sent pickup info, show it
                if (!response.getDay().isEmpty() && !response.getTimeslot().isEmpty()) {
                    System.out.println("Pickup: " + response.getDay() + " at " + response.getTimeslot());
                }
                System.out.println();
            }

            @Override
            public void onError(Throwable t) {
                // Handle different types of errors that could happen
                Status status = Status.fromThrowable(t);
                switch (status.getCode()) {
                    case INVALID_ARGUMENT:
                        System.err.println("Invalid input: " + status.getDescription());
                        break;
                    case UNAUTHENTICATED:
                        System.err.println("Unauthorized: " + status.getDescription());
                        break;
                    case DEADLINE_EXCEEDED:
                        System.err.println("Timeout: server too slow.");
                        break;
                    default:
                        System.err.println("Error: " + status);
                }
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                // When the server is done sending messages
                System.out.println("Server finished sending messages.");
                latch.countDown();
            }
        });

        // Wait for the server to finish responding
        latch.await(10, TimeUnit.SECONDS);

        // Close the connection
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
