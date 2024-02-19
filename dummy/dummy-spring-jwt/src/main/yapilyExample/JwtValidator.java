import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;

import static org.apache.hc.core5.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class JwtValidator {

    private static final String PUBLIC_KEY_PATH = "/.well-known/jwks.json";
    private static final String BEARER = "Bearer ";

    private final URI baseUrl;
    private final ObjectMapper objectMapper;

    public boolean isValid(WebhookMessage webhookMessage) {
        var jwt = extractJwt(webhookMessage);
        var jwtPayload = extractPayload(jwt);
        var validTime = validateTime(jwtPayload);
        if (!validTime) {
            log.error("Invalid notification time");
            return false;
        }

        var validSignature = validateSignature(jwt);
        if (!validSignature) {
            log.error("Invalid notification signature");
            return false;
        }

        var validateBody = validateBody(jwtPayload, webhookMessage.getOrderMessage().toString());
        if (!validateBody) {
            log.error("Invalid notification body");
            return false;
        }

        return true;
    }

    private SignedJWT extractJwt(WebhookMessage webhookMessage) {
        String authorization = authorisationHeader(webhookMessage)
                .orElseThrow(() -> new RuntimeException("Missing Authorisation Header"));
        String bearer = authorization.substring(BEARER.length());
        try {
            return SignedJWT.parse(bearer);
        } catch (ParseException ex) {
            throw new RuntimeException("Error during JWT extraction", ex);
        }
    }

    private boolean validateSignature(SignedJWT jwt) {
        try {
            for (ECKey publicKey : loadPublicKeys()) {
                JWSVerifier verifier = new ECDSAVerifier(publicKey);
                if (jwt.verify(verifier)) {
                    return true;
                }
            }
        } catch (JOSEException ex) {
            throw new RuntimeException("Error during JWT validation", ex);
        }
        return false;
    }

    private JwtPayload extractPayload(SignedJWT jwt) {
        try {
            return objectMapper.readValue(jwt.getPayload().toString(), JwtPayload.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error during JWT payload extraction", ex);
        }
    }

    private boolean validateTime(JwtPayload payload) {
        var now = Instant.now();
        var startCondition = now.compareTo(payload.creationTime());
        var endCondition = now.compareTo(payload.expirationTime());
        return startCondition >= 0 && endCondition <= 0;
    }

    private boolean validateBody(JwtPayload payload, String messageBody) {
        try {
            var messageDigest = MessageDigest.getInstance(payload.algorithm());
            var digest = messageDigest.digest(messageBody.getBytes());
            var hexDigest = HexFormat.of().formatHex(digest);
            return payload.digest().equals(hexDigest);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error during notification body validation");
        }
    }

    /** either due to sender or to transformation along the way, kwy mey come lower case */
    private Optional<String> authorisationHeader(WebhookMessage webhookMessage) {
        String authorisation = webhookMessage.getHeaders().get(AUTHORIZATION);
        if (authorisation == null) {
            authorisation = webhookMessage.getHeaders().get(AUTHORIZATION.toLowerCase());
        }
        return Optional.ofNullable(authorisation);
    }

    private List<ECKey> loadPublicKeys() {
        try {
            // use the HttpClinet to get the proxy out of the box, and then JWKSet.parse(string) from the GET call
            var list = JWKSet.load(baseUrl.resolve(PUBLIC_KEY_PATH).toURL())
                    .getKeys()
                    .stream()
                    .filter(key -> key instanceof ECKey)
                    .map(JWK::toECKey)
                    .toList();
            if (list.isEmpty()) {
                log.error("Unexpectedly, no EC public keys were found for Yapily");
            }
            return list;
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
