package com.alok.project.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.AuthorizationScope;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
//import sun.reflect.annotation.TypeAnnotation.TypeAnnotationTargetInfo;

//once youre done with swaggger config you can easily also change what the fronten shows you so we have made the swaggerconfig clkass for that
//all configs can be done according to you
//and alsonow jwt type auth work also neends tobe done in the swagger as the person cannot provodentoken in the swagger apis and hence we need to 
//do sth about that as well
//
@Configuration
public class SwaggerConfig {
	
	//now what we want is that the authorization header kinda thing to work on swaggger basically somehwere to put the token(jwt) somwhere
	public static final String AUTHORIZATION_HEADER ="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContext(){   //security conmtext wala method
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}
	
	
	private List<SecurityReference> securityReferences(){
		
		springfox.documentation.service.AuthorizationScope scope = new  springfox.documentation.service.AuthorizationScope("global", "accessEverything"); 
		return Arrays.asList(new SecurityReference("JWT", new springfox.documentation.service.AuthorizationScope[] {scope}));  //annonymous array is put here
	}
	
	//yha saari beans bana k kaam ho jayega 
	
	//now here all the configs are done using something callled as docket
	@Bean
	public Docket api() {
		
		
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext())       //ye security context wali cheez hai 
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	private ApiInfo getInfo() {
		 return new ApiInfo("BLOGGING APPLICATION : Backend Course", "This project is developed by Alok Verma", "1.0", "Terms of Service",
				 new Contact("Alok Verma", "https://github.com/alokv6109/Blog-Application", "valokv6109@gmail.com"),
				 "License of APIs", "API Licemse URL",
				 Collections.emptyList());
		 
	}
	
	
	

}
