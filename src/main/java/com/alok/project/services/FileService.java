package com.alok.project.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	/**
	 * 
	 * @param path = paht of the file
	 * @param file = the file will be taken by this 
	 * @return the name of the file ie being uploaded by the client on the server
	 * @throws IOException
	 */
	String uploadImage(String path, MultipartFile file) throws IOException;
	
	/**
	 * 
	 * @param path  = 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	InputStream getResource(String path, String fileName) throws FileNotFoundException;
	

}
