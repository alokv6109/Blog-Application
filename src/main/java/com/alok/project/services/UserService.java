package com.alok.project.services;

import java.util.List;

import com.alok.project.payloads.UserDto;

public interface UserService {
	
	//register a user
	UserDto registerNewUser(UserDto user); 

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
}
