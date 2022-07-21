package com.alok.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.project.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
