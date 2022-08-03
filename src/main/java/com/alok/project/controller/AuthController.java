package com.alok.project.controller;

import java.util.Date;

import javax.validation.Valid;

//import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alok.project.exceptions.LoginPasswordException;
import com.alok.project.payloads.ChangePasswordRequest;
//import com.alok.project.payloads.ChangePasswordRequst;
import com.alok.project.payloads.JwtAuthRequest;
import com.alok.project.payloads.JwtAuthResponse;
import com.alok.project.payloads.UserDto;
import com.alok.project.security.JwtTokenHelper;
import com.alok.project.services.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception
	{
	
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken authenticatonToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticatonToken);
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			System.out.println("invalid details!!");
			throw new LoginPasswordException("invalid password!!!");
			
		}
			
		
			//if things go ssouth then  the exception will nbe generated and te excetiuon be generated disbaled e 
			//taken care in gkobal exceptionj	
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto user){
	
		UserDto registeredUser = this.userService.registerNewUser(user);
		
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/changePass")
	public ResponseEntity<UserDto> changePassword(@Valid @RequestBody ChangePasswordRequest cpr, @Valid @RequestParam String email){
		UserDto registeredUser  = this.userService.createNewPassword(email, cpr.getPassword());
		Date d  =  new Date();
		cpr.setDate(d);
		System.out.println("the date when the password is changes is : " + cpr.getDate());
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.OK);
	}
	
}
