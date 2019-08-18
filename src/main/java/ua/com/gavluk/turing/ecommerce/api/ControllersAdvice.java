package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.gavluk.turing.ecommerce.exceptions.CommonException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class ControllersAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // TODO: write throwing some InternalException extends CommonException

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    ResponseEntity<?>  handleCommonException(HttpServletRequest request, Throwable e) {
        CommonException commonEx = (CommonException)e;
        HashMap<String, CommonException> body = new HashMap<>();
        body.put("error", commonEx);
        return new ResponseEntity<>(body, HttpStatus.resolve(commonEx.getStatus()));
    }
}
