package recyclingsupport;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RecyclingSupportClient {

    public static void main(String[] args) throws Exception {
        // We try to find the server on the local network using service discovery.
        RecyclingSupportServiceDiscovery.ServiceDetails service =
                RecyclingSupportServiceDiscovery.discover("RecyclingSupport");

        // If the server is not found, we stop the program.
        if (service == null) {
            System.err.println("Service 'RecyclingSupport' not found!");
            return;
        }

        // Here we connect to the server using the discovered host and port.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(service.host, service.port)
                .usePlaintext() // We don’t use encryption here (for local development).
                .build();

        // We create a stub to send and receive messages asynchronously.
        RecyclingSupportServiceGrpc.RecyclingSupportServiceStub asyncStub =
                RecyclingSupportServiceGrpc.newStub(channel);

        // This helps us wait until the chat is done before closing everything.
        CountDownLatch latch = new CountDownLatch(1);

        // We create a stream to handle incoming messages from the server.
        StreamObserver<ChatMessage> requestObserver = asyncStub.chat(new StreamObserver<ChatMessage>() {

            // This method is called when the server sends a message to the client.
            @Override
            public void onNext(ChatMessage value) {
                System.out.println("[Server " + value.getSender() + "] " + value.getMessage());
            }

            // This is called if there is an error during the chat.
            @Override
            public void onError(Throwable t) {
                System.err.println("Error during chat: " + t);
                latch.countDown(); // We stop waiting because something went wrong.
            }

            // This method is called when the server ends the chat.
            @Override
            public void onCompleted() {
                System.out.println("Chat ended by server.");
                latch.countDown(); // We can finish the program now.
            }
        });

        // We use Scanner to get user input from the terminal.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start chatting with the support agent (type 'exit' to quit):");

        // This is the chat loop. It keeps running until the user types "exit".
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            // If the user wants to exit, we tell the server and break the loop.
            if (userInput.equalsIgnoreCase("exit")) {
                requestObserver.onCompleted(); // Finish the chat.
                break;
            }

            // We build a message with the user's input.
            ChatMessage message = ChatMessage.newBuilder()
                    .setSender("client") // We identify ourselves as "client".
                    .setMessage(userInput)
                    .setTimestamp(java.time.Instant.now().toString())
                    .build();

            // We send the message to the server.
            requestObserver.onNext(message);
        }

        // We wait for the server to finish, or timeout after 1 minute.
        latch.await(1, TimeUnit.MINUTES);

        // We close the connection to the server.
        channel.shutdown();
    }
}
