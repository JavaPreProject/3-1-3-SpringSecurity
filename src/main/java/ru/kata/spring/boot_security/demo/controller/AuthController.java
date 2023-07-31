package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
public class AuthController {
    private final UserServiceImp usersServiceImp;

    @Autowired
    public AuthController(UserServiceImp userService) {
        this.usersServiceImp = userService;
    }

    @GetMapping("/")
    private String showWelcomePage() {
        return "welcome";
    }

    @GetMapping("/login")
    public String getAuthenticated() {
        return "login";
    }
}
