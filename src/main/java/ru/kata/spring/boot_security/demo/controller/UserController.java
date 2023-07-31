package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
public class UserController {
    private final UserServiceImp usersServiceImp;

    @Autowired
    public UserController(UserServiceImp userService) {
        this.usersServiceImp = userService;
    }


    @GetMapping("/user")
    public String showUserInfo(Model model) {
        User user = usersServiceImp.findOne();
        model.addAttribute("user", user);
        return "user";
    }
}
