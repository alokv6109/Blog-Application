package com.alok.project.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDto {
	//remeber that postdto and post should gave the name name of the variables
	//or else a null value cold be shiwn 
	//dtos and entity ka name has to be same 
	
	private int commentId;
	
	private String content;
	
	private UserForCommentsDto user;

}
