package com.alok.project.repositories;

import java.util.Optional;

//import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.project.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email); //this is basically for doinbg the basic auth work
	//and for this the username is baiscally your email and password is password
	//you will find out the email frpom the db and for that this methid is introduced
	
	

}
