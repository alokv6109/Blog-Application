package com.alok.project.exceptions;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.alok.project.payloads.ApiResponse;

//iss class mei saare k saare exeption agar koi aayi toh accordingly ek mathod hoga jo trigger ho jayea ussse basically humare user ko apne screeen pe acha sa message mil jayeag
//rather than an error
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	//toh below wale method se basicallly apke resource wale custom eexeption k liye ek chez jaya karegi toh ki dikhne mei error na ho kar ke kuch aur aaayega
	///aur finally issi tarah se jab bhi koi service ko resource nhi milega wala exception jayega toh ye method chalega
	//although aur bhi koi exception hota toh usko bhi aise hi mamange karte hum
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse  =new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> mp  = new HashMap<>();
		List<ObjectError> ls =    ex.getBindingResult().getAllErrors();
		for(ObjectError error: ls) {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			mp.put(fieldName, message);
			
		}
		
		return new ResponseEntity<Map<String,String>>(mp, HttpStatus.BAD_REQUEST );
		
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiResponse> maxUploadSizeExceededException(MaxUploadSizeExceededException ex){
		String message= ex.getLocalizedMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
	}
	// for the bvasic auth
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<ApiResponse> authenticationFailedException(InternalAuthenticationServiceException ex){
		String message=  ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
	//for the jwt auth
	@ExceptionHandler(LoginPasswordException.class)
	public ResponseEntity<ApiResponse> authenticationFailedJWTException(LoginPasswordException ex){
		String message=  ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse> accessDeniedException(AccessDeniedException ex){
		String message=  ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.FORBIDDEN);
	}

}
