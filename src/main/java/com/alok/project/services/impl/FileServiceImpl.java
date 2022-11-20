package com.alok.project.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alok.project.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		/**
		 * file ka path mil gya hai , basica;lly wo hum controller se de denge 
		 * and file swaayam bhi mil gyi hai hume 
		 */
		//file name nikalna hai
		String name = file.getOriginalFilename();
		//lkets say name is abc.png, now basically we dont want the same file name s is uploaded rather a hash value to be ysed
		
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//so basicallt filename1 is the final file name of the hased values
		
		//full path till the file name 
		String filePath = path + File.separator + fileName1; //fiel.seperator mka mtlb ho gya "/" ie path/fileName aisa but iski speciality ye hai k linux hua toh "\" ho jayega khud hi 
		
		
		//create folder ie(/images if not created previously) so that you can easily upload file over there, but after first upload ypou will surely get the folder 
		//once first is done and hence it is sth you do check kindaa thing
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//finally upload the thing(or you can say copy the file from the given file in args to the area)
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
	
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
