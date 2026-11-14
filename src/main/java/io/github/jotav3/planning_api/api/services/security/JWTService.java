package io.github.jotav3.planning_api.api.services.security;

import io.github.jotav3.planning_api.api.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class JWTService {

    private static final String ALGORITHM = "HmacSHA256";

    @Value("${app.jwt.secret}")
    private String secret;

    @Getter
    @Value("${app.jwt.expiration-seconds}")
    private long expirationSeconds;

    // PUBLIC

    public String generateToken(User user){
        String header = generateHeader();
        String payload = generatePayload(user);
        String signature = generateSignature(header, payload);

        return header + "." + payload + "." + signature;
    }

    // PRIVATE

    private String generateSignature(String header, String payload){
        String content = header + "." + payload;

        try{
            Mac mac = Mac.getInstance(ALGORITHM);

            // secret key
            byte[] bytesSecret = secret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(bytesSecret, ALGORITHM);
            mac.init(secretKey);

            byte[] signatureBytes = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));

            return toBase64URL(signatureBytes);

        } catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String generatePayload(User user){
        long expiresAt = Instant.now().plusSeconds(expirationSeconds).getEpochSecond();

        return toBase64URL(
                """
                {
                   "sub": "%s",
                   "name": "%s",
                   "exp": %d
                }
                """.formatted(
                        user.getId().toString(),
                        user.getName(),
                        expiresAt
                )
        );
    }

    private String generateHeader(){
        return toBase64URL(
                """
                {
                   "alg": "HS256",
                   "typ": "JWT"
                }
                """
        );
    }

    private String toBase64URL(String value){
        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String toBase64URL(byte[] byteArray){
        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(byteArray);
    }
}
