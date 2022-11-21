package com.alok.project.services;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.alok.project.entities.Post;
//import com.alok.project.payloads.PageResponse;
import com.alok.project.payloads.PostDto;
import com.alok.project.payloads.PostResponse;
import com.alok.project.payloads.UserPostResponse;

public interface PostService {

	//all the methods in the interface are public in nature
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);
	
	PostDto getPostById(Integer postId);
	
	
	
	//all te post by a category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	PostResponse getPostbyCategory(Integer pageNumber, Integer pageSize, Integer categoryId, String sortBy);
	
	//all the post by a user
	UserPostResponse getPostbyUser(Integer pageNumber, Integer pageSize, String email); //with pagination implkemented
	List<PostDto> getPostByUser(String email); //w/o paginaiton impleented
	//search post
	List<PostDto> searchPost(String keyword);

	
}
