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
        ConePurchaseServiceDiscovery.ServiceDetails service = ConePurchaseServiceDiscovery.discover();

        if (service == null) {
            System.err.println("Service 'ConePurchase' not found!");
            return;
        }

        System.out.println("Service found at: " + service.host + ":" + service.port);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(service.host, service.port)
                .usePlaintext()
                .build();

        // Metadata de autenticación
        Metadata metadata = new Metadata();
        Metadata.Key<String> AUTH_TOKEN_KEY = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(AUTH_TOKEN_KEY, "securetoken123");

        ConePurchaseServiceGrpc.ConePurchaseServiceStub rawStub =
                ConePurchaseServiceGrpc.newStub(channel);
        rawStub = MetadataUtils.attachHeaders(rawStub, metadata);

        Scanner scanner = new Scanner(System.in);

        // ✅ Validar cantidad
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

        // ✅ Solo pedir ubicación si el pedido es >= 100 conos
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
            location = "N/A"; // Dummy location para cumplir con el schema
        }

        ConeSaleRequest request = ConeSaleRequest.newBuilder()
                .setQuantity(quantity)
                .setLocation(location)
                .build();

        // Deadline aplicado
        ConePurchaseServiceGrpc.ConePurchaseServiceStub asyncStub =
                rawStub.withDeadlineAfter(3, TimeUnit.SECONDS);

        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("\nSending request to server...\n");

        asyncStub.requestConeSale(request, new StreamObserver<ConeSaleResponse>() {
            @Override
            public void onNext(ConeSaleResponse response) {
                System.out.println("[" + response.getMessageType() + "] " + response.getMessage());

                if (!response.getDay().isEmpty() && !response.getTimeslot().isEmpty()) {
                    System.out.println("Pickup: " + response.getDay() + " at " + response.getTimeslot());
                }
                System.out.println();
            }

            @Override
            public void onError(Throwable t) {
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
                System.out.println("Server finished sending messages.");
                latch.countDown();
            }
        });

        latch.await(10, TimeUnit.SECONDS);
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
