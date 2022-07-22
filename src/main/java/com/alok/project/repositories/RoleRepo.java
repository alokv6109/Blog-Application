package com.alok.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok.project.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
