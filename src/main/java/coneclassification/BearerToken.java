package coneclassification;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

// This class is used by the client to send the JWT token with every gRPC call
public class BearerToken extends CallCredentials {

    private final String value;

    public BearerToken(String value) {
        this.value = value;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {
                // Prepare metadata with Authorization header in format: Bearer <token>
                Metadata headers = new Metadata();
                headers.put(Constants.AUTHORIZATION_METADATA_KEY,
                        String.format("%s %s", Constants.BEARER_TYPE, value));
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // Required by gRPC, no implementation needed
    }
}
