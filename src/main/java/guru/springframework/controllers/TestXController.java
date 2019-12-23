package guru.springframework.controllers;

import guru.springframework.domain.Difficulty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestController
public class TestXController {

    @GetMapping(path = {"/testx"})
    public String testMethod(@RequestParam Difficulty difficulty) {
        log.info("{}", difficulty);
        return "Ok";
    }

    @PostMapping(path = {"/test"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String testMethod(TestClass testClass) {
        log.info("{}", testClass);
        return testClass.toString();
    }

}

@Data
@NoArgsConstructor
class TestClass {
    private Difficulty difficulty;
    private String name;
}
