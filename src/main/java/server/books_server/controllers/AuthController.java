package server.books_server.controllers;

import org.springframework.web.bind.annotation.*;
import server.books_server.entities.User;
import server.books_server.records.LoginRequest;
import server.books_server.records.RegisterRequest;
import server.books_server.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {
        return userService.register(req.username(), req.email(), req.password());
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest req) {
        return userService.login(req.email(), req.password());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }
}
