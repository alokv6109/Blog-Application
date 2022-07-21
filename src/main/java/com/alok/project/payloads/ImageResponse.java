package com.alok.project.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageResponse {
	
	private PostDto postDto;
	
	private Date imageDate;
	
	private String message;
	
	
	
	
	
	
}
