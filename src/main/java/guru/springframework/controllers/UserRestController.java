package guru.springframework.controllers;

import guru.springframework.security.dao.User;
import guru.springframework.security.dao.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController extends AbstractRestController{

    private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/users")
    public User createNewUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Херовый пользователь");
        }
        User save = userRepository.save(user);
        return save;
    }
}
