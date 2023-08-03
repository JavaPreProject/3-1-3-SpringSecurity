package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.NameValidator;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;
    private final NameValidator nameValidator;


    @Autowired
    public AdminController(RoleService roleService, UserService userService, NameValidator nameValidator) {
        this.roleService = roleService;
        this.userService = userService;
        this.nameValidator = nameValidator;
    }

    @GetMapping("/admin/user")
    public String showAllUsers(Model model) {
        model.addAttribute("user", userService.findAll());
        return "all_users";
    }

    @GetMapping("/admin/{id}")
    public String showOneUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "show";
    }

    @GetMapping("/admin/new")
    public String showPageCreatingUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", roleService.getRoles());
        return "new";
    }

    @PostMapping("/admin/user")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
       nameValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/{id}/update")
    public String showPageEditUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("role", roleService.getRoles());
        return "update";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        nameValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "update";
        }
        userService.update(user);
        return "redirect:user";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:user";
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
