package com.alok.project.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	private Date date;
	
	@NotEmpty
	@Size(min = 3, max  =10, message="Password must be min of 3 characters and max of 10 characters!!")
	private String password;

}
