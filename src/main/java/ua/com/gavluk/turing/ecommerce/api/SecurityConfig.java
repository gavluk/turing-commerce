package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Filter jwtRequestFilter;

    @Autowired
    public SecurityConfig(Filter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                // customer authorization required
                .antMatchers("/stripe/**").hasRole(CustomerAuthentication.ROLE)
                .antMatchers("/orders/**").hasRole(CustomerAuthentication.ROLE)
                .antMatchers(HttpMethod.GET, "/customers").hasRole(CustomerAuthentication.ROLE)
                .antMatchers(HttpMethod.POST, "/products/*/reviews").hasRole(CustomerAuthentication.ROLE)

                // for all
                .anyRequest().permitAll()

                // no sessions
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // error handling by turing standard
                .and().exceptionHandling().authenticationEntryPoint(new ControllersAdvice.AuthenticationEntryPointImple())
        ;

        // JWT authentication filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
