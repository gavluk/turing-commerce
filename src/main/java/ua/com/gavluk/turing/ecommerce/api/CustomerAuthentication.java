package ua.com.gavluk.turing.ecommerce.api;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.com.gavluk.turing.ecommerce.core.Customer;
import ua.com.gavluk.turing.ecommerce.exceptions.CommonException;

import java.util.Arrays;

public class CustomerAuthentication extends AbstractAuthenticationToken{
    public static final String ROLE = "CUSTOMER";
    private final String jwt;
    private final JWTClaimsSet jwtClaims;
    private final Customer customer;

    public CustomerAuthentication(String jwt, JWTClaimsSet jwtClaims, Customer customer) {
        super(Arrays.asList(
                    new SimpleGrantedAuthority(ROLE), // for working hasAuthority()
                    new SimpleGrantedAuthority("ROLE_" + ROLE) // for working hasRole()
                ));
        this.jwt = jwt;
        this.jwtClaims = jwtClaims;
        this.customer = customer;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }

    @Override
    public Object getPrincipal() {
        return customer.getEmail();
    }

    public String getJwt() {
        return jwt;
    }

    public JWTClaimsSet getJwtClaims() {
        return jwtClaims;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "CustomerAuthentication{" +
                "customer=" + customer +
                '}';
    }
}
