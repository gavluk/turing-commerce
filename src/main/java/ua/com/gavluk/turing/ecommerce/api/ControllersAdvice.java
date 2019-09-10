package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.CommonException;
import ua.com.gavluk.turing.ecommerce.exceptions.InternalErrorException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllersAdvice {

    private Logger logger = LoggerFactory.getLogger(ControllersAdvice.class);

    static class AuthenticationEntryPointImple implements AuthenticationEntryPoint {

        private final Logger logger;

        public AuthenticationEntryPointImple() {
            this.logger = LoggerFactory.getLogger(this.getClass());
        }

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
            logger.error("Auth error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
            CommonException commonEx = new AuthException(AuthException.ACCESS_UNAUTHORIZED);

            HashMap<String, CommonException> body = new HashMap<>();
            body.put("error", commonEx);

            response.getWriter().print(new ObjectMapper().writeValueAsString(body));
            response.setContentType("application/json");
            response.setStatus(commonEx.getStatus());
        }
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleServletRequestBindingException(HttpServletRequest request, Throwable ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
        CommonException commonEx = new InternalErrorException(InternalErrorException.UNKNOWN_ERROR);

        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleMessageNotReadableException(HttpServletRequest request, Throwable ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
        CommonException commonEx = new ValidationException(ValidationException.BAD_REQUEST, "http_message");

        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }


    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, Throwable ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
        CommonException commonEx = new ValidationException(ValidationException.BAD_REQUEST, "http_method");

        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }

/*
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(HttpServletRequest request, AuthenticationException ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
        CommonException commonEx = new AuthException(AuthException.ACCESS_UNAUTHORIZED);
        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }
*/

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
        String badParams = ex.getParameter().getParameterName();
        CommonException commonEx = new ValidationException(ValidationException.BAD_PARAMETER, badParams);
        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);

        BindingResult result = ex.getBindingResult();
        String badParams = result.getFieldErrors().stream().map(err -> err.getField()).collect(Collectors.joining(","));

        CommonException commonEx;
        if (result.getFieldError().getDefaultMessage().startsWith(ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX)) {
            String[] split = result.getFieldError().getDefaultMessage().split(":", 3);
            if (split.length < 3)
                commonEx = new ValidationException(
                        ValidationException.BAD_PARAMETER,
                        result.getFieldError().getField() + ":" + result.getFieldError().getDefaultMessage()
                );
            else
                // must be like "VALIDATION:USR_XX:Some message"
                commonEx = new ValidationException(new CommonException.Profile(
                    HttpStatus.BAD_REQUEST.value(),
                    split[1],
                    split[2]
                ), badParams);
        }
        else {
            commonEx = new ValidationException(ValidationException.BAD_PARAMETER, badParams);
        }

        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }


    @ResponseBody
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(HttpServletRequest request, BindException ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);

        BindingResult result = ex.getBindingResult();
        String badParams = result.getFieldErrors().stream().map(err -> err.getField()).collect(Collectors.joining(","));

        CommonException commonEx;
        if (result.getFieldError().getDefaultMessage().startsWith(ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX)) {
            String[] split = result.getFieldError().getDefaultMessage().split(":", 3);
            if (split.length < 3)
                commonEx = new ValidationException(
                        ValidationException.BAD_PARAMETER,
                        result.getFieldError().getField() + ":" + result.getFieldError().getDefaultMessage()
                );
            else
                // must be like "VALIDATION:USR_XX:Some message"
                commonEx = new ValidationException(new CommonException.Profile(
                        HttpStatus.BAD_REQUEST.value(),
                        split[1],
                        split[2]
                ), badParams);
        }
        else {
            commonEx = new ValidationException(ValidationException.BAD_PARAMETER, badParams);
        }

        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    ResponseEntity<?>  handleCommonException(HttpServletRequest request, Throwable ex) {
        logger.error("Error on " + request.getMethod() + " " + request.getRequestURI() + ":" + ex.getMessage(), ex);
        CommonException commonEx = (CommonException)ex;
        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }

}
