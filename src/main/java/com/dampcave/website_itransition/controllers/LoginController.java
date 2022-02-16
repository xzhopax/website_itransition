package com.dampcave.website_itransition.controllers;

import com.dampcave.website_itransition.service.UserRegistrationRepr;
import com.dampcave.website_itransition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginUser( Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/test")
    public String test( Model model) {
        model.addAttribute("title", "Login");
        return "test";
    }

    @GetMapping("/")
    public String home( Model model) {
        model.addAttribute("title", "Main");
        return "home";
    }

    @GetMapping("/oops")
    public String oops( Model model) {
        model.addAttribute("title", "Don't worry");
        return "dont-worry";
    }

    @GetMapping("/register")
    public String registrationUserGet(Model model){
        model.addAttribute("title", "Registration");
        UserRegistrationRepr userRegistrationRepr = new UserRegistrationRepr();
        model.addAttribute("user", userRegistrationRepr);
        model.addAttribute("people", userRegistrationRepr);
        return "register";
    }

    @PostMapping("/register")
    public String registrationNewUserPost(
            @Valid UserRegistrationRepr userRegistrationRepr, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "register";
        }

        if (!userRegistrationRepr.getPassword().equals(userRegistrationRepr.getRepeatPassword())){
            bindingResult.rejectValue("password","", "passwords not equals");
            return "register";
        }

        userService.create(userRegistrationRepr);
        return "redirect:/login";
    }

}
