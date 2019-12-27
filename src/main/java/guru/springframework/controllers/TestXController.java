package guru.springframework.controllers;

import guru.springframework.domain.Difficulty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
