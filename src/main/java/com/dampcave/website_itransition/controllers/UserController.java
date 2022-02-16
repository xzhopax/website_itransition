package com.dampcave.website_itransition.controllers;

import com.dampcave.website_itransition.models.People;
import com.dampcave.website_itransition.models.User;
import com.dampcave.website_itransition.repositoryes.PeopleRepository;
import com.dampcave.website_itransition.repositoryes.UserRepository;
import com.dampcave.website_itransition.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {


    private PeopleRepository peopleRepository;

    private UserRepository userRepository;

    private UserAuthService userAuthService;

    @Autowired
    public UserController(PeopleRepository peopleRepository, UserRepository userRepository, UserAuthService userAuthService) {
        this.peopleRepository = peopleRepository;
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("title", "All Users");
        Iterable<People> peoples = peopleRepository.findAll();
        model.addAttribute("peoples", peoples);
        return "users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model) {
        People people = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(people);
        return "redirect:/users";
    }

    @GetMapping("/users/deleteall")
    public String deleteAllUsers() {
        peopleRepository.deleteAll();
        return "redirect:/users";
    }

    @GetMapping("/users/blocked")
    public String userBlockedAll(HttpServletRequest request, HttpServletResponse response) {
        List<People> users = peopleRepository.findAll();
        for (People people : users) {
            people.getUser().setAccountNonLocked(false);
            peopleRepository.save(people);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        return "redirect:/login?logout";
    }

    @GetMapping("/users/unblocked")
    public String userUnblockedAll() {
        List<People> users = peopleRepository.findAll();
        for (People people : users) {
            people.getUser().setAccountNonLocked(true);
            peopleRepository.save(people);
        }
        return "redirect:/users";
    }


    @RequestMapping(value = "/users/unblocked/{id}", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request,
                             HttpServletResponse response,
                             @PathVariable(value = "id") Long id) {
        People people = peopleRepository.findById(id).orElseThrow();
        people.getUser().setAccountNonLocked(!people.getUser().isAccountNonLocked());

        peopleRepository.save(people);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (people.getUser().getUsername().equals(auth.getName())) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return "redirect:/login?logout";
        }

        return "redirect:/users";
    }

//    @GetMapping()
//    public ModelAndView getCheckUser(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("users");
//        boolean allCheckbox = false;
//
//        modelAndView.addObject("allCheckbox", allCheckbox);
//        return modelAndView;
//    }

//    @PostMapping()
//    public ModelAndView postBlockedUser(@ModelAttribute("allCheckbox") boolean allCheckbox,
//                                          BindingResult bindingResult)
//    {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("users");
//        if(!allCheckbox) {
//            List<People> users = peopleRepository.findAll();
//
//            for (People people : users){
//                userBlockedAll();
//                people.getUser().setAccountNonLocked(false);
//                peopleRepository.save(people);
//            }
//        }
//        return modelAndView;
//    }

//    @PostMapping("/usersblock")
//    public String blockedOnCheck(@ModelAttribute User user, @RequestParam("idChecked") List<String> idUsers){
//
//        if(idUsers != null){
//            for(String idCheckedStr : idUsers){
//                Long idrate = Long.parseLong(idCheckedStr);
//                People people = peopleRepository.findById(idrate).orElseThrow();
//                people.getUser().setAccountNonLocked(false);
//                peopleRepository.save(people);
//            }
//        }
//        return "redirect:/users";
//    }


//    @ModelAttribute("blockedOnCheck")
//    public void blockedOnCheckModel(@RequestParam("idChecked") List<String> idUsers){
//        if(idUsers != null){
//            for(String idUserStr : idUsers){
//                Long idUser = Long.parseLong(idUserStr);
//                People people = peopleRepository.findById(idUser).orElseThrow();
//                people.getUser().setAccountNonLocked(false);
//                peopleRepository.save(people);
//            }
//        }
////        return "redirect:/users";

    ////    }
    @GetMapping("/usersblock")
    public String deleteDSS(@RequestParam(value = "isChecked", required = false) List<String> isChecked, Model model) {
        if (isChecked != null) {
            for (String str : isChecked) {
                Long id = Long.parseLong(str);
                User user = userRepository.getById(id);
                user.setAccountNonLocked(false);
                People people = peopleRepository.findByUserUsername(user.getUsername()).orElseThrow();
                peopleRepository.save(people);
            }
        }

        return "redirect:/users";
    }

//    @ModelAttribute("blockedOnCheck")
//    public String blockedOnCheckModel(@RequestParam("idChecked") List<String> idUsers) {
//        if (idUsers != null) {
//            for (String idUserStr : idUsers) {
//                Long idUser = Long.parseLong(idUserStr);
//                People people = peopleRepository.findById(idUser).orElseThrow();
//                people.getUser().setAccountNonLocked(false);
//                peopleRepository.save(people);
//            }
//        }
//        return "redirect:/users";
//    }


}
