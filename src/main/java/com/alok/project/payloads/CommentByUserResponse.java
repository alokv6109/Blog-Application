package com.alok.project.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentByUserResponse {
	
	private Integer commentId;
	
	private String content;

}
