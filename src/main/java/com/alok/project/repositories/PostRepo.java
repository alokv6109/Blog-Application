package com.alok.project.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.project.entities.Category;
import com.alok.project.entities.Post;
import com.alok.project.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	public List<Post> findByUser(User user);
//	
	public List<Post> findByCategory(Category category);
//	
	public Page<Post> findAllByUser(User user, Pageable pageable);
	
	public List<Post> findByTitleContaining(String title);
	

}
