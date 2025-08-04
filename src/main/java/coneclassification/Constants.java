package coneclassification;

import io.grpc.Context;
import io.grpc.Metadata;

public class Constants {

    // This is the metadata key used to read the Authorization header from the client request.
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    // This is the prefix that must be at the beginning of the token string: "Bearer ..."
    public static final String BEARER_TYPE = "Bearer";

    // This is the secret key used to sign and verify JWT tokens.
    public static final String JWT_SIGNING_KEY = "secret123";

    // This is the key used to store and access the client ID from the gRPC context.
    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY =
            Context.key("clientId");
}
