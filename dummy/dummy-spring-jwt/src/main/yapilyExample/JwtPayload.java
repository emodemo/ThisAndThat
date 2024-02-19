import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record JwtPayload(
        @JsonProperty("digest") String digest,
        @JsonProperty("enc") String algorithm,
        @JsonProperty("iat") Instant creationTime,
        @JsonProperty("exp") Instant expirationTime) {
}
