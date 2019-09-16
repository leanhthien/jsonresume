package com.example.demo.controllers;

import com.example.demo.entity.AppUser;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/registration")
    public String listUser(Model model) {
        model.addAttribute("user", new AppUser());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveOrUpdateUser(AppUser user) {
        AppUser savedUser = userService.saveOrUpdateUser(user);
        return "redirect:/userInfo";
    }

}
