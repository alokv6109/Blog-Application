package com.alok.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok.project.payloads.ApiResponse;
import com.alok.project.payloads.CommentByUserResponse;
import com.alok.project.payloads.CommentDto;
import com.alok.project.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.OK);
		
	}
	

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> createComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("The comment deleted succesfully!!", true), HttpStatus.OK);
		
	}
	
	// just for testing purpose dont use it
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> getAllComments(){
		List<CommentDto> allComments = this.commentService.getAllComments();
		
		return new ResponseEntity<List<CommentDto>>(allComments, HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}/comments")
	public ResponseEntity<List<CommentByUserResponse>> getAllUsersComments(@PathVariable Integer userId){
		List<CommentByUserResponse> response = this.commentService.getCommentsOfUser(userId);
		return new ResponseEntity<List<CommentByUserResponse>>(response, HttpStatus.OK);
	}
}
