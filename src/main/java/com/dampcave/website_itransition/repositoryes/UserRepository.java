package com.dampcave.website_itransition.repositoryes;


import com.dampcave.website_itransition.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {


}