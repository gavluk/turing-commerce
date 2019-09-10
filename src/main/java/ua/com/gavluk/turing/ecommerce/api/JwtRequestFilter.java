package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.gavluk.turing.ecommerce.core.Customer;
import ua.com.gavluk.turing.ecommerce.core.CustomerService;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.CommonException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

// inspired by https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String BEARER = "Bearer";
    public static final String AUTH_HEADER_NAME = "USER-KEY";

    private final JwtUtils jwtUtils;
    private CustomerService customerService;

    @Autowired
    public JwtRequestFilter(JwtUtils jwtUtils, CustomerService customerService) {
        this.jwtUtils = jwtUtils;
        this.customerService = customerService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTH_HEADER_NAME);
        if (authHeader != null && authHeader.startsWith(BEARER)) {
            String jwt = authHeader.substring(BEARER.length()+1);
            try {
                JWTClaimsSet jwtClaims = jwtUtils.validateToken(jwt);
                Customer customer = this.customerService.findById(Long.valueOf(jwtClaims.getSubject())).orElseThrow(
                        () -> new NotFoundException(NotFoundException.CUSTOMER_NOT_FOUND)
                );
                SecurityContextHolder.getContext().setAuthentication(new CustomerAuthentication(jwt, jwtClaims, customer));

            } catch (CommonException commonEx) {
                logger.warn("Error authenticating by JWT token", commonEx);
                HashMap<String, CommonException> body = new HashMap<>();
                body.put("error", commonEx);

                response.getWriter().print(new ObjectMapper().writeValueAsString(body));
                response.setContentType("application/json");
                response.setStatus(commonEx.getStatus());
                return; // broke the chain here!
            }
        }

        filterChain.doFilter(request, response);
    }
}
