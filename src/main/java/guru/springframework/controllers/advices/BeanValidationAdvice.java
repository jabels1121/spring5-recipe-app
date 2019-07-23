package guru.springframework.controllers.advices;

import guru.springframework.controllers.responses.ErrorResponse;
import guru.springframework.controllers.responses.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class BeanValidationAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> fieldValidationHandler(RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        error.setMessage(ex.getMessage());
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setType("BAD_REQUEST");
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setError(error);
        genericResponse.initResult();
        return handleExceptionInternal(ex, genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
