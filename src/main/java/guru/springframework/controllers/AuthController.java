package guru.springframework.controllers;

import guru.springframework.commands.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/signUp")
    public ModelAndView registrationForm() {
        return new ModelAndView("registration_page", "user", new UserRegistrationDto());
    }

}
