package coneclassification;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import coneclassification.ConeClassificationServiceGrpc.ConeClassificationServiceImplBase;
import coneclassification.ConeInfo;
import coneclassification.Suggestion;

public class ConeClassificationServer extends ConeClassificationServiceImplBase {

    // This logger helps show messages in the server console
    private static final Logger logger = Logger.getLogger(ConeClassificationServer.class.getName());

    public static void main(String[] args) {
        
        // Create the server implementation
        ConeClassificationServer serviceImpl = new ConeClassificationServer();
        int port = 50053;

        try {
            // Start the gRPC server on the specified port
            Server server = ServerBuilder.forPort(port)
                    .addService(ServerInterceptors.intercept(serviceImpl, new AuthorizationServerInterceptor()))
                    .build()
                    .start();

            // Show a message that the server is running
            logger.info("ConeClassificationServer started, listening on port " + port);
            System.out.println("ConeClassificationServer started, listening on port " + port);

            // Register the service using JmDNS so clients can find it
            ConeClassificationServiceRegistration.getInstance()
                .registerService("_grpc._tcp.local.", "ConeClassifier", port, "Cone Classification Service");

           server.awaitTermination();
           
           
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Show any errors if the server fails
        }
    }

    // This method is called when a client sends a classifyCone request
    @Override
    public void classifyCone(ConeInfo request, StreamObserver<Suggestion> responseObserver) {
        try {
            // Print a message to show that the request was received
            System.out.println("Received classifyCone request");

            // Get the data from the request and convert it to lowercase
            String type = request.getType().toLowerCase();
            String size = request.getSize().toLowerCase();
            String condition = request.getCondition().toLowerCase();

            // Validate the type: it must be "plastic" or "cardboard"
            if (!(type.equals("plastic") || type.equals("cardboard"))) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid type: must be 'plastic' or 'cardboard'")
                        .asRuntimeException());
                return;
            }

            // Validate the size: it must be "small" or "large"
            if (!(size.equals("small") || size.equals("large"))) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid size: must be 'small' or 'large'")
                        .asRuntimeException());
                return;
            }

            // Validate the condition: it must be "good" or "damaged"
            if (!(condition.equals("good") || condition.equals("damaged"))) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid condition: must be 'good' or 'damaged'")
                        .asRuntimeException());
                return;
            }

            // Decide the suggestion based on the condition, type, and size
            String suggestion;
            if (condition.equals("damaged")) {
                if (type.equals("plastic") && size.equals("small")) {
                    suggestion = "Recycle - plastic shredder line";
                } else {
                    suggestion = "Discard - unrepairable";
                }
            } else {
                if (type.equals("plastic")) {
                    // Large plastic cones go to Soltex, small ones can be reused
                    suggestion = size.equals("large") ? "Sell to Soltex" : "Reusable";
                } else {
                    // Cardboard cones in good condition go to paper recycling
                    suggestion = "Compress and send to paper recycler";
                }
            }

            // Build the response with the suggestion
            Suggestion response = Suggestion.newBuilder()
                    .setSuggestion(suggestion)
                    .build();

            // Send the response back to the client
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            // If something goes wrong, send an internal error to the client
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error: " + e.getMessage())
                    .augmentDescription("Please contact support.")
                    .asRuntimeException());
        }
    }
}
