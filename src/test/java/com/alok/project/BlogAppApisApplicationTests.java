package com.alok.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alok.project.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void repotest(){
		String clssName=  this.userRepo.getClass().getName();
		String packName  = this.userRepo.getClass().getPackageName();
		System.out.println(clssName);
		System.out.println(packName);
	}
//	

}
