package coneclassification;

import io.grpc.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import coneclassification.ConeClassificationServiceGrpc;
import coneclassification.ConeInfo;
import coneclassification.Suggestion;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class ConeClassificationClient {
    // Logger to show logs (not actively used here)
    private static final Logger logger = Logger.getLogger(ConeClassificationClient.class.getName());

    public static void main(String[] args) throws Exception {

        // Discover the service on the network
        ConeClassificationServiceDiscovery.ServiceDetails service =
                ConeClassificationServiceDiscovery.discover("ConeClassifier");

        // If not found, show an error and exit
        if (service == null) {
            System.err.println("Service 'ConeClassifier' not found!");
            return;
        }

        // Get host and port from the discovered service
        String host = service.host;
        int port = service.port;
        System.out.println("Discovered service at " + host + ":" + port);

        // Create a gRPC channel to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Use plaintext since we're running locally
                .build();

        // Generate a signed JWT token
        String jwt = getJwt();

        // Create a CallCredentials object that holds the token
        BearerToken token = new BearerToken(jwt);

        // Create the stub and attach the token credentials
        ConeClassificationServiceGrpc.ConeClassificationServiceBlockingStub rawStub =
                ConeClassificationServiceGrpc.newBlockingStub(channel)
                        .withCallCredentials(token);

        // Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt user for cone type until valid input is given
        String type;
        do {
            System.out.print("Enter cone type (plastic/cardboard): ");
            type = scanner.nextLine().trim().toLowerCase();
            if (!type.equals("plastic") && !type.equals("cardboard")) {
                System.out.println("Invalid type. Please enter 'plastic' or 'cardboard'.");
            }
        } while (!type.equals("plastic") && !type.equals("cardboard"));

        // Prompt user for cone size until valid input is given
        String size;
        do {
            System.out.print("Enter cone size (small/large): ");
            size = scanner.nextLine().trim().toLowerCase();
            if (!size.equals("small") && !size.equals("large")) {
                System.out.println("Invalid size. Please enter 'small' or 'large'.");
            }
        } while (!size.equals("small") && !size.equals("large"));

        // Prompt user for cone condition until valid input is given
        String condition;
        do {
            System.out.print("Enter cone condition (good/damaged): ");
            condition = scanner.nextLine().trim().toLowerCase();
            if (!condition.equals("good") && !condition.equals("damaged")) {
                System.out.println("Invalid condition. Please enter 'good' or 'damaged'.");
            }
        } while (!condition.equals("good") && !condition.equals("damaged"));

        // Build the request object using the collected input
        ConeInfo request = ConeInfo.newBuilder()
                .setType(type)
                .setSize(size)
                .setCondition(condition)
                .build();

        try {
            // Add a deadline to avoid long waiting
            ConeClassificationServiceGrpc.ConeClassificationServiceBlockingStub blockingStub =
                    rawStub.withDeadlineAfter(3, TimeUnit.SECONDS);

            // Send the request and get the server response
            Suggestion response = blockingStub.classifyCone(request);

            // Show the suggestion from the server
            System.out.println("Server suggestion: " + response.getSuggestion());

        } catch (StatusRuntimeException e) {
            // Handle known gRPC error types
            switch (e.getStatus().getCode()) {
                case INVALID_ARGUMENT:
                    System.err.println("Invalid input: " + e.getStatus().getDescription());
                    break;
                case UNAUTHENTICATED:
                    System.err.println("Unauthorized: " + e.getStatus().getDescription());
                    break;
                case DEADLINE_EXCEEDED:
                    System.err.println("Deadline exceeded. The server took too long to respond.");
                    break;
                case INTERNAL:
                    System.err.println("Server error: " + e.getStatus().getDescription());
                    break;
                default:
                    System.err.println("RPC failed: " + e.getStatus());
            }
        } finally {
            // Close the channel after the work is done
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    // This method creates a signed JWT with the subject 'GreetingClient'
    private static String getJwt() {
        return Jwts.builder()
                .setSubject("GreetingClient") // Identifier for the client (subject)
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SIGNING_KEY)
                .compact(); // Return the final encoded token
    }
}
