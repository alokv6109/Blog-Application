package com.alok.project.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.ResourceAccessException;

import com.alok.project.entities.Category;
import com.alok.project.entities.Post;
import com.alok.project.entities.User;
import com.alok.project.exceptions.ResourceNotFoundException;
import com.alok.project.payloads.CategoryDto;
//import com.alok.project.payloads.PageResponse;
import com.alok.project.payloads.PostDto;
import com.alok.project.payloads.PostResponse;
import com.alok.project.payloads.UserPostResponse;
import com.alok.project.repositories.CategoryRepo;
import com.alok.project.repositories.PostRepo;
import com.alok.project.repositories.UserRepo;
import com.alok.project.services.PostService;

//import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user  = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		
		Category category = this.categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		
		Post post  = this.modelmapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelmapper.map(newPost, PostDto.class);
	}
	
	

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		
		Category cat = this.categoryrepo.findById(postDto.getCategory().getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", postDto.getCategory().getCategoryId()));
		
		post.setCategory(cat);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setAddedDate(new Date());
		Post updatedPost = this.postRepo.save(post);
		return this.modelmapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
				
		this.postRepo.delete(post);
		
	}
//without paginf and sorting
	
//	@Override
//	public List<PostDto> getAllPost() {
//		List<Post> posts = this.postRepo.findAll();
//		List<PostDto> postDtos  =new ArrayList<PostDto>();
//		for(Post p:posts) {
//			postDtos.add(this.modelmapper.map(p, PostDto.class));
//		}
//		return postDtos;
//	}
	
//	with apginbg and sorting
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy ) {
//		int pagesize = 5;
//		int pageNumber = 1;
//		Pageable prequest = PageRequest.of(pageNumber, pageSize);
//		everythin page and etc are from springframework domain thing
//		
//		Pageable prequest = PageRequest.of(pageNumber, pageSize, org.springframework.data.domain.Sort.by(sortBy)); //in ascendinf order
		Pageable prequest = PageRequest.of(pageNumber, pageSize, org.springframework.data.domain.Sort.by(sortBy).descending());
		
		Page<Post> page = this.postRepo.findAll(prequest);
		List<Post> posts  = page.getContent();
		
		List<PostDto> postDtos  =new ArrayList<PostDto>();
		for(Post p:posts) {
			postDtos.add(this.modelmapper.map(p, PostDto.class));
		}
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPagesize(page.getSize());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setLastpage(page.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		return this.modelmapper.map(post, PostDto.class);
	}

	//implement pagination in here as well for posts by category
	//same with the user aswell but now i have made i better by using the pagination thing but rememebr that it was starting mei like this only
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category category = this.categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		List<Post> posts =  this.postRepo.findByCategory(category);
		List<PostDto> postDtos  =new ArrayList<PostDto>();
		for(Post p:posts) {
			postDtos.add(this.modelmapper.map(p, PostDto.class));
		}
		return postDtos;
	}
	
	
	@Override
	public PostResponse getPostbyCategory(Integer pageNumber, Integer pageSize, Integer categoryId, String sortBy) {
		// TODO Auto-generated method stub
		Category category = this.categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		Pageable prequest = PageRequest.of(pageNumber, pageSize, org.springframework.data.domain.Sort.by(sortBy).descending());
		Page<Post> page = this.postRepo.findAllByCategory(category, prequest);
		List<Post> posts  = page.getContent();
		
		List<PostDto> postDtos  =new ArrayList<PostDto>();
		for(Post p:posts) {
			postDtos.add(this.modelmapper.map(p, PostDto.class));
		}
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPagesize(page.getSize());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setLastpage(page.isLast());
		System.out.println("the page number request was made " + new Date());
		return postResponse;
		
	}
	
	
	

	//implkemet pagination over here as well with post by user
	@Override
	public UserPostResponse getPostbyUser(Integer pageNumber, Integer pageSize,String email) {
		
		Pageable pagereq = PageRequest.of(pageNumber, pageSize);
//		Integer userId  = this.userRepo.findIdByEmail(email);
		
		User user = this.userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email : " + email, 0));
		
		Page<Post> pagePost = this.postRepo.findAllByUser(user, pagereq);
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos  =new ArrayList<PostDto>();
		for(Post p:posts) {
			postDtos.add(this.modelmapper.map(p, PostDto.class));
		}
		
		UserPostResponse userPostResponse = new UserPostResponse();
		userPostResponse.setContent(postDtos);
		userPostResponse.setLastpage(pagePost.isLast());
		userPostResponse.setPageNumber(pagePost.getNumber());
		userPostResponse.setPagesize(pagePost.getSize());
		userPostResponse.setTotalElements(pagePost.getTotalElements());
		userPostResponse.setTotalPages(pagePost.getTotalPages());
		
		return userPostResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {

		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = new ArrayList<PostDto>();
		for(Post p:posts) {
			postDtos.add(this.modelmapper.map(p, PostDto.class));
		}
		return postDtos;
	}


	//service for getPost of the logged in user using the auth token w/o agination implemented
	@Override
	public List<PostDto> getPostByUser(String email) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email : " + email, 0));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = new ArrayList<PostDto>();
		for(Post p:posts) {
			postDtos.add(this.modelmapper.map(p, PostDto.class));
		}
		
		return postDtos;
	}



	



	

}
