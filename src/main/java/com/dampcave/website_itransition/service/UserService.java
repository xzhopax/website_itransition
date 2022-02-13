package com.dampcave.website_itransition.service;

import com.dampcave.website_itransition.models.People;
import com.dampcave.website_itransition.models.User;
import com.dampcave.website_itransition.repositoryes.PeopleRepository;
import com.dampcave.website_itransition.repositoryes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    private PeopleRepository peopleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PeopleRepository peopleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }




    public void create(UserRegistrationRepr userRegistrationRepr){
        User user = new User();
        People people = new People();
        user.setUsername(userRegistrationRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationRepr.getPassword()));
        people.setName(userRegistrationRepr.getName());
        people.setEmail(userRegistrationRepr.getEmail());
        people.setUser(user);
        peopleRepository.save(people);
//        userRepository.save(user);

    }
}
