package com.alok.project.controller;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alok.project.config.AppConstants;
import com.alok.project.entities.Post;
import com.alok.project.payloads.ApiResponse;
import com.alok.project.payloads.ImageResponse;
//import com.alok.project.payloads.ImagerResponse;
import com.alok.project.payloads.PostDto;
import com.alok.project.payloads.PostResponse;
import com.alok.project.payloads.UserPostResponse;
import com.alok.project.services.FileService;
import com.alok.project.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createdPost  = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	//get  all posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<UserPostResponse> getPostsByUser(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false ) Integer pageSize,@PathVariable Integer userId){
		UserPostResponse userPostResponse = this.postService.getPostbyUser(pageNumber, pageSize, userId);
		return new ResponseEntity<UserPostResponse>(userPostResponse, HttpStatus.OK);
	}
	
	//get all posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	//get all the posts
	//with paging and  sorting impemented
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false ) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY, required = false ) String sortBy){
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	// get single the posts
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
		PostDto post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("the post is deleted", true), HttpStatus.OK);
	}
		
	//update the post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto, @PathVariable Integer postId){
		PostDto post = this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		List<PostDto> result = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}
	
	//upload image API
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<ImageResponse> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto post =  this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image); //path is taken rom the appliation props file and then you k ow where to upload the image and image file will be given by you explicitly
		//above is giving us the name of the file that  has been uploaded to the images folder in the project
		
		post.setImageName(fileName);
		PostDto postdto = this.postService.updatePost(post, postId);
		
		ImageResponse imgResponse = new ImageResponse();
		imgResponse.setPostDto(postdto);
		imgResponse.setImageDate(postdto.getAddedDate());
		imgResponse.setMessage("The image has been uploaded successfully!");
		return new ResponseEntity<ImageResponse>(imgResponse, HttpStatus.OK);
	}
	
	//download from the server image file
	@GetMapping(value=  "/posts/image/{imageName}", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
	

}
