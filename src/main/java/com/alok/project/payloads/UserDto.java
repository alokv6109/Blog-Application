package com.alok.project.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.alok.project.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	//qki saara data aarha hai request body nmei dto se toh issliye dto ko validate kia jayega 
	
	
	private int id;
	
	//checks name as well as not empty thing
	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 character!!")
	private String name;
	
	@Email(message = "Email address is not valid !!")
	@NotEmpty
	private String email;
	
	@JsonIgnoreProperties
	@NotEmpty
	@Size(min = 3, max  =10, message="Password must be min of 3 characters and max of 10 characters!!")
//	@Pattern(regexp = "^[a-z0-9]", message = "Can only contain the letters from a-z small case and numbers 0-9!!")
	private String password;
	
	@NotEmpty
	private String about;
	
//	private Set<RoleDto> roles = new HashSet<RoleDto>();
	
//	private List<CommentDto> comments = new ArrayList<CommentDto>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password =password;
	}
	
	
	
	
}
