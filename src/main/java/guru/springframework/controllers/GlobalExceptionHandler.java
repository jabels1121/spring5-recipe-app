package guru.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "guru/springframework/controllers")
@Slf4j
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

   /* @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getLocalizedMessage());
        return super.handleBindException(ex, headers, status, request);
    }*/

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = BAD_REQUEST)
    public @ResponseBody String bindExc(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(ex.getLocalizedMessage());

        return ex.getLocalizedMessage();
    }
}
