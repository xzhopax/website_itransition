package com.dampcave.website_itransition.controllers;


import com.dampcave.website_itransition.models.People;
import com.dampcave.website_itransition.repositoryes.PeopleRepository;
import com.dampcave.website_itransition.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {


    private PeopleRepository peopleRepository;

    private UserAuthService userAuthService;

    @Autowired
    public UserController(PeopleRepository peopleRepository, UserAuthService userAuthService) {
        this.peopleRepository = peopleRepository;
        this.userAuthService = userAuthService;
    }

    @GetMapping("/index")
    public String allUsers(Model model){
        model.addAttribute("title", "All Users");
        Iterable<People> peoples = peopleRepository.findAll();
        model.addAttribute("peoples", peoples);
        return "index";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable(value = "id") Long id,Model model){
        People people = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(people);
        return "redirect:/index";
    }

    @GetMapping("/users/deleteall")
    public String deleteAllUsers(){
        peopleRepository.deleteAll();
        return "redirect:/index";
    }

    @GetMapping("/users/blocked")
    public String userBlockedAll(){
        List<People> users = peopleRepository.findAll();
        for (People people : users){
            people.getUser().setAccountNonLocked(false);
            peopleRepository.save(people);
        }
        return "redirect:/index";
    }

    @GetMapping("/users/unblocked")
    public String userUnblockedAll(){
        List<People> users = peopleRepository.findAll();
        for (People people : users){
            people.getUser().setAccountNonLocked(true);
            peopleRepository.save(people);
        }
        return "redirect:/index";
    }


    @GetMapping("/users/unblocked/{id}")
    public String userIsAccountNonLocked(@PathVariable(value = "id") Long id,Model model){
        People people = peopleRepository.findById(id).orElseThrow();
        people.getUser().setAccountNonLocked(!people.getUser().isAccountNonLocked());

        peopleRepository.save(people);
        return "redirect:/index";
    }

}
