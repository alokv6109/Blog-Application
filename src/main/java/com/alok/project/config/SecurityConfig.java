package com.alok.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.alok.project.security.CustomUserDetailService;
import com.alok.project.security.JwtAuthenticationEntryPoint;
import com.alok.project.security.JwtAuthenticationFilter;

/**
 * this file is basically for doing the basic authentoication
 * and once youre done with it the javascript ka form will come on the browse
 * and in your postman you can also do the authorizaion sing the authorization->basic Auth->fill in the username and passs there 
 * and youre ready to go so now by this you can test all your apis successfully, wheterr get or post
 * @author user
 *
 */

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//saare urls ko ek saath public walo ko saath mei hi rakh lo
	
	public static final String[] PUBLIC_URLS = {
			"/api/auth/**", "/v3/api-docs/", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/v2/api-docs"
	};
	
	//once youre done with swaggger config you can easily also change what the fronten shows you so we have made the swaggerconfig clkass for that
	
	//remember all of this is basic auth  only just that the complete thing is done by the help of user stored in the db
	//now we will processd wih the jwt authentication
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	//this mwthod is used for the suth of the apis
	//anf this auth is for the apis and all apis will work in this manner
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * antmatchers se jaise ye login ki api ka bataya hai k sab login kar sake taaki toh wo sahi hai 
		 * theek ussi tarah se lets say tum chahte ho k get ki apis k liye authorizartion na chhaiye ho toh wo bhi ho sakta hai 
		 * 
		 */
		http.
		csrf().disable()
		.authorizeHttpRequests()
		.antMatchers(PUBLIC_URLS).permitAll()   //** for login and register both without difference in roles etc
		.antMatchers(HttpMethod.GET).permitAll() //for permitting all the et methods
		.anyRequest()
		.authenticated()
		.and()        //for the jwt token thing you need to add sth before the http basics
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		.httpBasic();  //http basic authenticatoon has come in place
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	// this method is for configuring that the authorization will basically be done by the db
	//so =yuo need to tell in this that the custome userdetail servoice ka method of loadbyusername to do the work via db
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	//this is for the password encoder to do th encodeing of the paaword
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//JWT authentication  configuration
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	
	
	
	

}
