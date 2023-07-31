package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import ru.kata.spring.boot_security.demo.util.NewUserValidator;

import javax.validation.Valid;


@Controller
public class AdminController {
    private final RoleServiceImp rolesServiceImp;
    private final UserServiceImp usersServiceImp;
    private final NewUserValidator newUserValidator;



    @Autowired
    public AdminController(RoleServiceImp roleService, UserServiceImp userService, NewUserValidator newUserValidator) {
        this.rolesServiceImp = roleService;
        this.usersServiceImp = userService;
        this.newUserValidator = newUserValidator;
    }

    @GetMapping("/admin/user")
    public String showAllUsers(Model model) {
        model.addAttribute("user", usersServiceImp.findAll());
        return "all_users";
    }

    @GetMapping("/admin/{id}")
    public String showOneUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersServiceImp.findOne(id));
        return "show";
    }

    @GetMapping("/admin/new")
    public String showPageCreatingUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", rolesServiceImp.getRoles());
        return "new";
    }

    @PostMapping("/admin/user")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        newUserValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        usersServiceImp.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/{id}/update")
    public String showPageEditUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", usersServiceImp.findOne(id));
        model.addAttribute("role", rolesServiceImp.getRoles());
        return "update";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        usersServiceImp.update(user);
        return "redirect:user";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        usersServiceImp.delete(id);
        return "redirect:user";
    }
}
