package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(path = {"/", "", "/index", "index.html"})
    public String indexPage() {
        return "index";
    }

}

