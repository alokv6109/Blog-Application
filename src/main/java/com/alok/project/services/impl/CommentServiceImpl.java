package com.alok.project.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok.project.entities.Comment;
import com.alok.project.entities.Post;
import com.alok.project.entities.User;
import com.alok.project.exceptions.ResourceNotFoundException;
import com.alok.project.payloads.CommentByUserResponse;
import com.alok.project.payloads.CommentDto;
import com.alok.project.payloads.UserDto;
import com.alok.project.payloads.UserForCommentsDto;
import com.alok.project.repositories.CommentRepo;
import com.alok.project.repositories.PostRepo;
import com.alok.project.repositories.UserRepo;
import com.alok.project.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		User user = this.modelMapper.map(commentDto.getUser(), User.class);
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		CommentDto com = this.modelMapper.map(savedComment, CommentDto.class);
		com.setUser(commentDto.getUser());
		
		return com;
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment Id", commentId));
		
		this.commentRepo.delete(comment);
		
		
	}

	@Override
	public List<CommentDto> getAllComments() {
		List<Comment> comments = this.commentRepo.findAll();
		List<CommentDto> commentDtos =  new ArrayList<CommentDto>();
		for(Comment c: comments) {
			User user = c.getUser();
			CommentDto mappedDto = this.modelMapper.map(c, CommentDto.class);
			mappedDto.setUser(this.modelMapper.map(user, UserForCommentsDto.class));
			
			commentDtos.add(mappedDto);
		}
		return commentDtos;
	}

	@Override
	public List<CommentByUserResponse> getCommentsOfUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));
		List<Comment> comments = user.getComments();
		List<CommentByUserResponse> response =  new ArrayList<CommentByUserResponse>();
		for(Comment c: comments) {
			response.add(this.modelMapper.map(c, CommentByUserResponse.class));
		}
		
		return response;
		
	}
	
	

}
