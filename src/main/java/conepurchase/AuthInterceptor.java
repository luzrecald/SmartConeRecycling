package conepurchase;

import io.grpc.*;

public class AuthInterceptor implements ServerInterceptor {
    private static final String EXPECTED_TOKEN = "securetoken123";

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
        ServerCall<ReqT, RespT> call,
        Metadata headers,
        ServerCallHandler<ReqT, RespT> next) {

        String token = headers.get(Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER));

        if (!EXPECTED_TOKEN.equals(token)) {
            call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing auth token"), new Metadata());
            return new ServerCall.Listener<ReqT>() {}; // Cancela la llamada
        }

        return next.startCall(call, headers);
    }
}