package recyclingsupport;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RecyclingSupportClient {

    public static void main(String[] args) throws Exception {
        // Discover service using JmDNS
        RecyclingSupportServiceDiscovery.ServiceDetails service =
                RecyclingSupportServiceDiscovery.discover("RecyclingSupport");

        if (service == null) {
            System.err.println("Service 'RecyclingSupport' not found!");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress(service.host, service.port)
                .usePlaintext()
                .build();

        RecyclingSupportServiceGrpc.RecyclingSupportServiceStub asyncStub =
                RecyclingSupportServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<ChatMessage> requestObserver = asyncStub.chat(new StreamObserver<ChatMessage>() {
            @Override
            public void onNext(ChatMessage value) {
                System.out.println("[Server " + value.getSender() + "] " + value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error during chat: " + t);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Chat ended by server.");
                latch.countDown();
            }
        });

        Scanner scanner = new Scanner(System.in);
        System.out.println("Start chatting with the support agent (type 'exit' to quit):");

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                requestObserver.onCompleted();
                break;
            }

            ChatMessage message = ChatMessage.newBuilder()
                    .setSender("client")
                    .setMessage(userInput)
                    .setTimestamp(java.time.Instant.now().toString())
                    .build();

            requestObserver.onNext(message);
        }

        latch.await(1, TimeUnit.MINUTES);
        channel.shutdown();
    }
}

