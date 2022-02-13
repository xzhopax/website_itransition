package com.dampcave.website_itransition.repositoryes;

import com.dampcave.website_itransition.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByUsername(String username);
}
