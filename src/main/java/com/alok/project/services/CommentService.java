package com.alok.project.services;

import com.alok.project.payloads.CommentByUserResponse;
import com.alok.project.payloads.CommentDto;

import antlr.collections.List;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	java.util.List<CommentDto> getAllComments();
	
	void deleteComment(Integer commentId);
	
	java.util.List<CommentByUserResponse> getCommentsOfUser(Integer userId);

}
