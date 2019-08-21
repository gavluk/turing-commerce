package ua.com.gavluk.turing.ecommerce.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ua.com.gavluk.turing.ecommerce.exceptions.CommonException;
import ua.com.gavluk.turing.ecommerce.exceptions.InternalErrorException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllersAdvice {

    private Logger logger = LoggerFactory.getLogger(ControllersAdvice.class);

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
        String badParams = ex.getParameter().getParameterName();
        CommonException commonEx = new ValidationException(ValidationException.BAD_PARAMETER, badParams);
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
        CommonException commonEx = new ValidationException(ValidationException.BAD_PARAMETER, badParams);
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
