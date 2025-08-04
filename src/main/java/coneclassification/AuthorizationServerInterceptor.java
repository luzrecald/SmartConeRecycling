
package coneclassification;

import io.grpc.*;
import io.jsonwebtoken.*;

// This interceptor checks the JWT token sent by the client in metadata
public class AuthorizationServerInterceptor implements ServerInterceptor {

    // Parser to validate JWT token with secret key
    private JwtParser parser = Jwts.parser().setSigningKey(Constants.JWT_SIGNING_KEY);

    // This method is called for every gRPC call to check the token
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {

        // Get the value of the Authorization header
        String value = metadata.get(Constants.AUTHORIZATION_METADATA_KEY);
        Status status;

        if (value == null) {
            // No token sent
            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!value.startsWith(Constants.BEARER_TYPE)) {
            // Token format is wrong
            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {
            try {
                // Extract the actual token string (remove 'Bearer' word)
                String token = value.substring(Constants.BEARER_TYPE.length()).trim();

                // Parse and validate the token using the secret key
                Jws<Claims> claims = parser.parseClaimsJws(token);

                // Save the client ID (subject) in the context
                Context ctx = Context.current().withValue(Constants.CLIENT_ID_CONTEXT_KEY, claims.getBody().getSubject());

                // Continue the call using the updated context
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            } catch (Exception e) {
                // Token is invalid or parsing failed
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
        }

        // If something failed, close the call with an authentication error
        serverCall.close(status, metadata);

        // Return an empty listener that does nothing
       return new ServerCall.Listener<ReqT>() {};
    }
}
