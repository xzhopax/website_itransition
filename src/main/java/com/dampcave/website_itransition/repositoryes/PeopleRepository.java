package com.dampcave.website_itransition.repositoryes;

import com.dampcave.website_itransition.models.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByUserUsername( String username);
}
