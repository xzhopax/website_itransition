package com.dampcave.website_itransition.controllers;


import com.dampcave.website_itransition.models.People;
import com.dampcave.website_itransition.repositoryes.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping("/index")
    public String allUsers(Model model){
        Iterable<People> peoples = peopleRepository.findAll();
        model.addAttribute("peoples", peoples);
        return "index";
    }



//    @GetMapping("/users/add")
//    public String usersAdd(Model model){
//        return "users-add";
//    }
//
//    @PostMapping("/users/add")
//    public String newUserAdd(@RequestParam String name, @RequestParam String email,
//                             @RequestParam String login, @RequestParam String password, Model model){
//        People people = new People(name, email);
//        peopleRepository.save(people);
//
//        return "redirect:/users";
//    }

    @GetMapping("/users/{id}")
    public String userDetails(@PathVariable(value = "id") Long id, Model model){
        if (!peopleRepository.existsById(id)){
            return "redirect:/user";
        }
        Optional<People> user = peopleRepository.findById(id);
        ArrayList<People> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "users-details";
    }

    @GetMapping("/users/{id}/update")
    public String userGetUpdate(@PathVariable(value = "id") Long id, Model model){
        if (!peopleRepository.existsById(id)){
            return "redirect:/users";
        }
        Optional<People> user = peopleRepository.findById(id);
        ArrayList<People> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "users-update";
    }

    @PostMapping("/users/{id}/update")
    public String userUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
                                 @RequestParam String email,Model model){
        People people = peopleRepository.findById(id).orElseThrow();
        peopleRepository.save(people);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable(value = "id") Long id,Model model){
        People post = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(post);
        return "redirect:/index";
    }

    @PostMapping("/users/deleteall")
    public String deleteAllUsers(){
        peopleRepository.deleteAll();
        return "redirect:/index";
    }
}
