package ua.com.gavluk.turing.ecommerce.api;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.jupiter.api.Test;
import ua.com.gavluk.turing.ecommerce.core.MockCustomer;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;


class JwtUtilsTest {


    public static final String JWT_SECRET_BASE64 = "c2VjcmV0IGtleSBteXN0IGJlIGF0IGxlYXN0IDMyIGNoYXJzICgyNTYgYml0cyk=";
    public static final String JWT_ISSUER = "Test Issuer";

    @Test
    void issuJwtTokenFor() throws AuthException, JOSEException {

        JwtUtils utils = new JwtUtils(10, JWT_ISSUER, JWT_SECRET_BASE64);
        String token = null;
        token = utils.issuJwtTokenFor(new MockCustomer(1L, "Jhon", "test@email.com"));

        System.out.println(token);

        JWTClaimsSet claims = utils.validateToken(token);

        assertEquals(JWT_ISSUER, claims.getIssuer());
        assertEquals("1", claims.getSubject());

        System.out.println(claims.toString());

    }
}