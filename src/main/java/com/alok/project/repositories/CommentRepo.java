package com.alok.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.project.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
