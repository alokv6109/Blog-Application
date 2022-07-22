package com.alok.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alok.project.entities.Role;
import com.alok.project.repositories.RoleRepo;

import org.modelmapper.ModelMapper;

//import com.sun.media.sound.ModelMappedInstrument;
//here the bean csan be declared because spring appl can also work as the configuration and bean class

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));
//		for saving the roles ont time
		
		Role role1=  new Role();
		role1.setId(1);
		role1.setName("ROLE_ADMIN");
		
		this.roleRepo.save(role1);
		
		Role role2=  new Role();
		role2.setId(2);
		role2.setName("ROLE_USER");
		
		this.roleRepo.save(role2);
		
		System.out.println("roles saved");
		
		
		
	}

}
