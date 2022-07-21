package com.alok.project.payloads;

import lombok.Data;
import lombok.Getter;

//data is better than lombok
@Data
public class JwtAuthResponse {
	
	private String token;

}
