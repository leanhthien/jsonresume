package com.example.demo.controllers;

import com.example.demo.domain.UserDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserController{

  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String showRegistrationForm(WebRequest request, Model model) {
      UserDto userDto = new UserDto();
      model.addAttribute("user", userDto);
      return "registration";
  }
}