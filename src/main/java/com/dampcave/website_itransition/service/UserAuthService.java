package com.dampcave.website_itransition.service;

import com.dampcave.website_itransition.repositoryes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.dampcave.website_itransition.models.User> myUser = userRepository.findByUsername(username);
        if (myUser.get().getEnabled()) {
            return userRepository.findByUsername(username)
                    .map(user -> new User(
                            user.getUsername(),
                            user.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority("USER"))
                    )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } else
            throw new UsernameNotFoundException("User blocked");
    }
}
