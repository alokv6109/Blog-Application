package com.alok.project.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.alok.project.entities.Category;
import com.alok.project.entities.Comment;
import com.alok.project.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user; //user that created the post
	
	private Set<CommentDto> comments = new HashSet<CommentDto>(); 
	

}
