package coneclassification;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import coneclassification.ConeClassificationServiceGrpc.ConeClassificationServiceImplBase;
import coneclassification.ConeInfo;
import coneclassification.Suggestion;

public class ConeClassificationServer extends ConeClassificationServiceImplBase {

    private static final Logger logger = Logger.getLogger(ConeClassificationServer.class.getName());

    public static void main(String[] args) {
        ConeClassificationServer serviceImpl = new ConeClassificationServer();
        int port = 50053;

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(ServerInterceptors.intercept(serviceImpl, new AuthInterceptor())) // 🔐 Interceptor de Auth
                    .build()
                    .start();

            logger.info("ConeClassificationServer started, listening on port " + port);
            System.out.println("ConeClassificationServer started, listening on port " + port);

            // Registrar servicio con JmDNS
            ConeClassificationServiceRegistration.getInstance()
                .registerService("_grpc._tcp.local.", "ConeClassifier", port, "Cone Classification Service");

            server.awaitTermination();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void classifyCone(ConeInfo request, StreamObserver<Suggestion> responseObserver) {
        try {
            System.out.println("Received classifyCone request");

            // Simula demora (descomentá si querés probar deadline)
            // Thread.sleep(5000);

            String type = request.getType().toLowerCase();
            String size = request.getSize().toLowerCase();
            String condition = request.getCondition().toLowerCase();

            // Validaciones
            if (!(type.equals("plastic") || type.equals("cardboard"))) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid type: must be 'plastic' or 'cardboard'")
                        .asRuntimeException());
                return;
            }

            if (!(size.equals("small") || size.equals("large"))) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid size: must be 'small' or 'large'")
                        .asRuntimeException());
                return;
            }

            if (!(condition.equals("good") || condition.equals("damaged"))) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Invalid condition: must be 'good' or 'damaged'")
                        .asRuntimeException());
                return;
            }

            // Lógica
            String suggestion;
            if (condition.equals("damaged")) {
                if (type.equals("plastic") && size.equals("small")) {
                    suggestion = "Recycle - plastic shredder line";
                } else {
                    suggestion = "Discard - unrepairable";
                }
            } else {
                if (type.equals("plastic")) {
                    suggestion = size.equals("large") ? "Sell to Soltex" : "Reuse in traffic control";
                } else {
                    suggestion = "Compress and send to paper recycler";
                }
            }

            Suggestion response = Suggestion.newBuilder()
                    .setSuggestion(suggestion)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error: " + e.getMessage())
                    .augmentDescription("Please contact support.")
                    .asRuntimeException());
        }
    }
}
