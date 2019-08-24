package ua.com.gavluk.turing.ecommerce.api;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.Customer;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;


@Service
public class JwtUtils {

    private final String issuer;
    private final MACSigner signer;
    private final byte[] secret;
    private final Logger logger;
    private int ttl;

    public JwtUtils(
            @Value("${jwt.ttl:30}") int ttl,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.secret.base64}") String secret
    ) throws KeyLengthException {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.issuer = issuer;
        this.ttl = ttl;
        this.secret = Base64.getDecoder().decode(secret);
        this.signer = new MACSigner(this.secret);
    }

    public String issuJwtTokenFor(Customer customer) throws JOSEException {

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(String.valueOf(customer.getId()))
                .issuer(this.issuer)
                .issueTime(Date.from(Instant.now()))
                .expirationTime(Date.from(Instant.now().plus(ttl, ChronoUnit.MINUTES)))
                .build();

        JWSObject token = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claims.toJSONObject()));

        token.sign(this.signer);

        return token.serialize();
    }

    public JWTClaimsSet validateToken(String token) throws AuthException {

        // https://connect2id.com/products/nimbus-jose-jwt/examples/validating-jwt-access-tokens
        ConfigurableJWTProcessor<SimpleSecurityContext> proc = new DefaultJWTProcessor<>();
        JWKSource<SimpleSecurityContext> keySource = new ImmutableSecret<>(this.secret);

        JWSKeySelector<SimpleSecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, keySource);
        proc.setJWSKeySelector(keySelector);

        JWTClaimsSet claims;
        try {
            claims = proc.process(token, null);
        } catch (ParseException | BadJOSEException | JOSEException e) {
            this.logger.warn(String.format("Error parsing JWT token '%s'", token), e);
            throw new AuthException(AuthException.INVALID_AUTH_TOKEN, e instanceof BadJOSEException ? "signature" : "token");
        }

        if (Instant.now().isAfter(claims.getExpirationTime().toInstant()) )
            throw new AuthException(AuthException.EXPIRED_AUTH_TOKEN);

        return claims;
    }
}
