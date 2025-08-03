package recyclingsupport;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import recyclingsupport.RecyclingSupportServiceGrpc.RecyclingSupportServiceImplBase;
import recyclingsupport.ChatMessage;

import java.io.IOException;
import java.time.Instant;
import java.util.logging.Logger;

public class RecyclingSupportServer extends RecyclingSupportServiceImplBase {

    private static final Logger logger = Logger.getLogger(RecyclingSupportServer.class.getName());

    public static void main(String[] args) {
        int port = 50054;
        RecyclingSupportServer serviceImpl = new RecyclingSupportServer();

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(serviceImpl)
                    .build()
                    .start();

            logger.info("RecyclingSupportServer started. Listening on port: " + port);

            // JmDNS registration
            RecyclingSupportServiceRegistration reg = RecyclingSupportServiceRegistration.getInstance();
            reg.registerService("_grpc._tcp.local.", "RecyclingSupport", port, "gRPC Recycling Support Chat");

            server.awaitTermination();

        } catch (IOException | InterruptedException e) {
            logger.severe("Server error: " + e.getMessage());
        }
    }

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessage> responseObserver) {
        return new StreamObserver<ChatMessage>() {

            private String clientName = null;

            @Override
            public void onNext(ChatMessage message) {
                if (clientName == null) {
                    clientName = message.getSender();
                    System.out.println("Client connected: " + clientName);
                }

                String receivedText = message.getMessage();
                System.out.println("[" + clientName + "]: " + receivedText);

                String replyText = generateSupportResponse(receivedText);

                ChatMessage reply = ChatMessage.newBuilder()
                        .setSender("SupportAgent")
                        .setMessage(replyText)
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(reply);

                if (receivedText.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected: " + clientName);
                    responseObserver.onCompleted();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Client error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Session closed with: " + clientName);
                responseObserver.onCompleted();
            }

            private String generateSupportResponse(String input) {
                input = input.toLowerCase();

                if (input.contains("hello") || input.contains("hi")) {
                    return "Hello! How can I assist you today?";
                } else if (input.contains("problem") || input.contains("issue") || input.contains("error")) {
                    return "I'm sorry to hear that. Could you please describe the issue in more detail?";
                } else if (input.contains("thank")) {
                    return "You're welcome! Do you need further assistance?";
                } else if (input.contains("bye") || input.contains("exit")) {
                    return "Goodbye! Thank you for contacting support.";
                } else {
                    return "I'm here to help! Could you clarify your message?";
                }
            }
        };
    }
}
