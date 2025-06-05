package hse.restaurant.repo.controller;

import hse.restaurant.repo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserController {

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user);

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody User user);

    @PostMapping("/logout")
    ResponseEntity<?> logoutUser(@RequestBody User user);
}
