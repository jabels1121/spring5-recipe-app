package guru.springframework.controllers;

import guru.springframework.commands.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
public class TestController extends AbstractRestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class.getName());

    @PostMapping(
            path = "/test",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Test test(@Valid Test test) {
        logger.info("Test object: {}", test);
        return test;
    }

    @GetMapping(path = "/getTest", headers = "Accept=*", produces = APPLICATION_JSON_VALUE)
    public String getTest(@RequestParam
                        @Size(min = 5, max = 10, message = "blabla")
                        String id) {
        return id;
    }

}
