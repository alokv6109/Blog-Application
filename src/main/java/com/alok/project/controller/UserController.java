package com.alok.project.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.xml.XmlResultProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok.project.payloads.ApiResponse;
import com.alok.project.payloads.UserDto;
import com.alok.project.security.JwtTokenHelper;
import com.alok.project.services.UserService;

/**
 * preauthorize laga k saare hi cheezo ko bata sakte hai har api ki usse kaun use karega 
 * 
 * 
 * @author user
 *
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	//String name=  this.jwtTokenHelper.getUsernameFromToken()
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId ){
		UserDto userDto = this.userService.getUserById(userId);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}
	
	
	
	//post create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto){
		UserDto createUserDto  = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//		return null;
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/registerAdmin")
	public ResponseEntity<UserDto> createAdmin( @Valid @RequestBody UserDto userDto){
		UserDto createUserDto  = this.userService.registerAdmin(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//		return null;
		
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	
	//admin can only call this thing / api
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted succesfully", true), HttpStatus.OK);
	}
	
	
}
