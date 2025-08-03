package coneclassification;

import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import coneclassification.ConeClassificationServiceGrpc;
import coneclassification.ConeInfo;
import coneclassification.Suggestion;

public class ConeClassificationClient {
    private static final Logger logger = Logger.getLogger(ConeClassificationClient.class.getName());

    public static void main(String[] args) throws Exception {

        // Descubrir servicio vía JmDNS
        ConeClassificationServiceDiscovery.ServiceDetails service =
                ConeClassificationServiceDiscovery.discover("ConeClassifier");

        if (service == null) {
            System.err.println("Service 'ConeClassifier' not found!");
            return;
        }

        String host = service.host;
        int port = service.port;
        System.out.println("Discovered service at " + host + ":" + port);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        // 🔐 Metadata con token
        Metadata metadata = new Metadata();
        Metadata.Key<String> AUTH_TOKEN_KEY = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(AUTH_TOKEN_KEY, "secrettoken123");

        // Stub base con metadata (sin deadline aún)
        ConeClassificationServiceGrpc.ConeClassificationServiceBlockingStub rawStub =
                ConeClassificationServiceGrpc.newBlockingStub(channel);
        rawStub = MetadataUtils.attachHeaders(rawStub, metadata);

        Scanner scanner = new Scanner(System.in);

        // Validaciones: no se avanza hasta que el input sea correcto
        String type;
        do {
            System.out.print("Enter cone type (plastic/cardboard): ");
            type = scanner.nextLine().trim().toLowerCase();
            if (!type.equals("plastic") && !type.equals("cardboard")) {
                System.out.println("Invalid type. Please enter 'plastic' or 'cardboard'.");
            }
        } while (!type.equals("plastic") && !type.equals("cardboard"));

        String size;
        do {
            System.out.print("Enter cone size (small/large): ");
            size = scanner.nextLine().trim().toLowerCase();
            if (!size.equals("small") && !size.equals("large")) {
                System.out.println("Invalid size. Please enter 'small' or 'large'.");
            }
        } while (!size.equals("small") && !size.equals("large"));

        String condition;
        do {
            System.out.print("Enter cone condition (good/damaged): ");
            condition = scanner.nextLine().trim().toLowerCase();
            if (!condition.equals("good") && !condition.equals("damaged")) {
                System.out.println("Invalid condition. Please enter 'good' or 'damaged'.");
            }
        } while (!condition.equals("good") && !condition.equals("damaged"));

        // Crear solicitud
        ConeInfo request = ConeInfo.newBuilder()
                .setType(type)
                .setSize(size)
                .setCondition(condition)
                .build();

        try {
            // ✅ Aplicar deadline justo antes de la llamada
            ConeClassificationServiceGrpc.ConeClassificationServiceBlockingStub blockingStub =
                    rawStub.withDeadlineAfter(3, TimeUnit.SECONDS);

            // Enviar solicitud
            Suggestion response = blockingStub.classifyCone(request);
            System.out.println("Server Suggestion: " + response.getSuggestion());

        } catch (StatusRuntimeException e) {
            switch (e.getStatus().getCode()) {
                case INVALID_ARGUMENT:
                    System.err.println("Invalid input: " + e.getStatus().getDescription());
                    break;
                case UNAUTHENTICATED:
                    System.err.println("Unauthorized: " + e.getStatus().getDescription());
                    break;
                case DEADLINE_EXCEEDED:
                    System.err.println("Deadline exceeded: the server took too long to respond.");
                    break;
                case INTERNAL:
                    System.err.println("Server error: " + e.getStatus().getDescription());
                    break;
                default:
                    System.err.println("RPC failed: " + e.getStatus());
            }
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
