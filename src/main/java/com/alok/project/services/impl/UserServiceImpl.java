package com.alok.project.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alok.project.exceptions.*;
import com.alok.project.config.AppConstants;
import com.alok.project.entities.Role;
import com.alok.project.entities.User;
import com.alok.project.payloads.UserDto;
import com.alok.project.repositories.RoleRepo;
//import com.alok.project.repositories.RoleRepository;
import com.alok.project.repositories.UserRepo;
import com.alok.project.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	/**
	 * yha pe jo cheez userdto to user nad uer to userdto k methods banaye gye hai wo librarry ie mapper library ki madata se actually karte hai par yha par aise 
	 * 
	 * method bana k kia gya hai 
	 * remember k har baar yha pe cheez dao pe user gya hai aur actual kaam humare userdto ne kia hai
	 */
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser  = this.userRepo.save(user);
		UserDto dto = this.userToDto(savedUser);
		return dto;
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		//the user nbelow would be optional, you can do anyhting else if the optional thing reutrns null
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", " id ", userId));
		//updateion thing takes palce
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser  =this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user= this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user", " Id ", userId));
		
		return this.userToDto(user);
	}

	
	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users  = this.userRepo.findAll();
		List<UserDto> userDtos  = new ArrayList<UserDto>();
		for(User u: users) {
			userDtos.add(this.userToDto(u));
		}
		//return null;
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user  = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", " Id ", userId));
		user.setRoles(null);
		this.userRepo.delete(user);
	}
	
	//model mappper se aur aasani se ho sakta hia ye kaam 
	//usssi aur aasan ho jaata hai
	private User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
//		User user  =new User();
		
		
//		user.setId(userDto.getId());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		
		return user;
		
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		UserDto userDto  =new UserDto();
//		userDto.setId(user.getId());
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		
		return userDto;
		
	}

	
	//for registering the user with the encoded paswrord and password given 
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//role to a person : if by register api then lets give it normal role only
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User savedUser = this.userRepo.save(user);
		
		return this.modelMapper.map(savedUser, UserDto.class);
	}

}
