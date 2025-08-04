package recyclingsupport;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import recyclingsupport.RecyclingSupportServiceGrpc.RecyclingSupportServiceImplBase;
import recyclingsupport.ChatMessage;

import java.io.IOException;
import java.time.Instant;
import java.util.logging.Logger;

// This is the server class for the recycling support chat.
// It allows users to chat with a virtual support agent about cone recycling.
public class RecyclingSupportServer extends RecyclingSupportServiceImplBase {

    private static final Logger logger = Logger.getLogger(RecyclingSupportServer.class.getName());

    public static void main(String[] args) {
        int port = 50054;
        RecyclingSupportServer serviceImpl = new RecyclingSupportServer();

        try {
            // Here we create the gRPC server on the specified port and add our chat service.
            Server server = ServerBuilder.forPort(port)
                    .addService(serviceImpl)
                    .build()
                    .start();

            // Print message to show the server is running.
            logger.info("RecyclingSupportServer started. Listening on port: " + port);

            // This part helps the server be discovered on the local network (service registration).
            RecyclingSupportServiceRegistration reg = RecyclingSupportServiceRegistration.getInstance();
            reg.registerService("_grpc._tcp.local.", "RecyclingSupport", port, "gRPC Recycling Support Chat");

            // Wait here so the server keeps running.
            server.awaitTermination();

        } catch (IOException | InterruptedException e) {
            logger.severe("Server error: " + e.getMessage());
        }
    }

    // This method allows the server to respond to messages from the client.
    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessage> responseObserver) {
        return new StreamObserver<ChatMessage>() {

            private String clientName = null;

            // This method is called when a message is received from the client.
            @Override
            public void onNext(ChatMessage message) {
                if (clientName == null) {
                    clientName = message.getSender(); // We save the client's name from the first message.
                    System.out.println("Client connected: " + clientName);
                }

                String receivedText = message.getMessage(); // We get the user's message.
                System.out.println("[" + clientName + "]: " + receivedText);

                // We use this function to decide how the chatbot should respond.
                String replyText = generateSupportResponse(receivedText);

                // We prepare the message to send back to the client.
                ChatMessage reply = ChatMessage.newBuilder()
                        .setSender("SupportAgent")
                        .setMessage(replyText)
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(reply); // Send the message.

                // If the user says "exit", we end the chat session.
                if (receivedText.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected: " + clientName);
                    responseObserver.onCompleted();
                }
            }

            // This method is called if something goes wrong during the chat.
            @Override
            public void onError(Throwable t) {
                System.err.println("Client error: " + t.getMessage());
            }

            // This is called when the client finishes the chat normally.
            @Override
            public void onCompleted() {
                System.out.println("Session closed with: " + clientName);
                responseObserver.onCompleted();
            }

            // This function creates the replies for the user based on what they type.
            private String generateSupportResponse(String input) {
            input = input.toLowerCase().trim();

            // Define regex patterns
            String greetingPattern = "\\b(hello|hi|hey|good (morning|afternoon|evening))\\b";
            String buyPattern = "\\b(buy|purchase|order|get)\\b";
            String reportPattern = "\\b(report|problem|issue|complain|waste|broken|damage)\\b";
            String classifyPattern = "\\b(classify|classification|material|type|sort|identify)\\b";
            String thanksPattern = "\\b(thank(s)?|appreciate|gracias)\\b";
            String exitPattern = "\\b(bye|exit|goodbye|quit|see you|farewell)\\b";

            // Match input to pattern
            if (input.matches(".*" + greetingPattern + ".*")) {
                return "Hello! Welcome to the Cone Recycling Support Center. How can I assist you today?";

            } else if (input.matches(".*" + buyPattern + ".*")) {
                return "Sure! You can buy recycled cones here. Just let me know the type you're interested in.";

            } else if (input.matches(".*" + reportPattern + ".*")) {
                return "Thank you for letting us know. Could you provide more details about the issue or waste you're reporting?";

            } else if (input.matches(".*" + classifyPattern + ".*")) {
                return "Of course! Let's classify your cones. Are they made of plastic, metal, cardboard, or another material?";

            } else if (input.matches(".*" + thanksPattern + ".*")) {
                return "You're welcome! Let me know if there's anything else I can help you with.";

            } else if (input.matches(".*" + exitPattern + ".*")) {
                return "Goodbye! Thanks for supporting sustainable cone recycling. See you next time!";

            } else {
                return "I'm here to help with cone recycling, purchases, reports, and classification. Try using words like 'buy', 'report', or 'classify'. Could you please clarify your message?";
            }
        }

        };
    }
}
