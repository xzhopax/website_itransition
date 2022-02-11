package com.dampcave.website_itransition.controllers;


import com.dampcave.website_itransition.models.User;
import com.dampcave.website_itransition.repositoryes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String allUsers(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }



    @GetMapping("/users/add")
    public String usersAdd(Model model){
        return "users-add";
    }

    @PostMapping("/users/add")
    public String newUserAdd(@RequestParam String name, @RequestParam String email,
                             @RequestParam String login, @RequestParam String password, Model model){
        User user = new User(name, email, login, password);
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String userDetails(@PathVariable(value = "id") int id, Model model){
        if (!userRepository.existsById(id)){
            return "redirect:/user";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "users-details";
    }

    @GetMapping("/users/{id}/update")
    public String userGetUpdate(@PathVariable(value = "id") int id, Model model){
        if (!userRepository.existsById(id)){
            return "redirect:/users";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "users-update";
    }

    @PostMapping("/users/{id}/update")
    public String userUpdate(@PathVariable(value = "id") int id, @RequestParam String name,
                                 @RequestParam String email,Model model){
        User user = userRepository.findById(id).orElseThrow();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable(value = "id") int id,Model model){
        User post = userRepository.findById(id).orElseThrow();
        userRepository.delete(post);
        return "redirect:/users";
    }

    @PostMapping("/users/deleteall")
    public String deleteAllUsers(){
        userRepository.deleteAll();
        return "redirect:/users";
    }
}
