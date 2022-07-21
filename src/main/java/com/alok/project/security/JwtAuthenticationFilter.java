package com.alok.project.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	//this will check the request before heading to the api that is the auth token correct or not 
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get tokem from the request
		String requestToken = request.getHeader("Authorization");
		//the token wuld be starting like below 
		// Bearer 2424234324324324324hj242j4432
		
		System.out.println(requestToken);
		
		String username=  null;
		String token=  null;
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("Unable t get JWT token");
			}
			catch(ExpiredJwtException e) {
				System.out.println("JWT token expired");
			}
			catch(MalformedJwtException e) {
				System.out.println("Invalid jwt token");
			}
			
		}
		else {
			System.out.println("jwt token does not begin with bearer !!");
		}
		
		//2) once we get the token we will now validatre the token
		if(username!=null  && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				//shi chal rha hai , mtlb validate succefull rha
				//authentication karenge ab using security contecxt holder ki help se
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//isme setSuthentocation k liye uska object banane padega with the help of usernamepassword authe token
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
				
			}else {
				System.out.println("Invalid jtw token");
			}
		}else {
				System.out.println("Username is null or security context is not null ");
			}
		
		filterChain.doFilter(request, response);
		}
	
		
	}

	

