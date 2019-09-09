package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

import static java.lang.String.format;

public class LoggingFilter extends AbstractRequestLoggingFilter {


    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        request.setAttribute("startedAt", Instant.now());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.logger.info(format("%s %s, auth:%s, query:%s",
                request.getMethod(),
                request.getRequestURI(),
                auth instanceof AnonymousAuthenticationToken ? "anonymous" : auth,
                request.getQueryString() == null ? "" : request.getQueryString()
                ));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        Instant startedAt = (Instant) request.getAttribute("startedAt");
        this.logger.info(format("%s %s finished, duration:%s ms",
                request.getMethod(),
                request.getRequestURI(),
                (Instant.now().toEpochMilli() - startedAt.toEpochMilli())
                ));
    }
}
