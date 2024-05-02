package com.nirvanamarket.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	public static void saveFile(String uploadDir, String fileName,  MultipartFile multipartFile) throws IOException
	{

		//BUILD THE PATH TO THE DIRECTORY
		Path uploadPath = Paths.get(uploadDir); 
		
		//CHECK IF THE PATH TO THE DIRECTORY EXISTS ON THIS MACHINE FS - If not exists - create new Dirctory
		if(!Files.exists(uploadPath))
		{
			Files.createDirectories(uploadPath);
		}
		
		
	
		//TRY WITH RESOURCES
		try(InputStream inputStream = multipartFile.getInputStream())
		{
			//CREATE THE PATH OF THE FILE - RELATIVE TO THE UPLOAD DIRECTORY
			Path  filePath = uploadPath.resolve(fileName);
			
			
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			
		}
		catch(IOException ex)
		{
			//user-photos/Ammanolla.png: Not a directory
			throw new IOException("Could not save file: "   + fileName, ex);
		}
	
	}
	
	public static void cleanDir(String dir) 
	{
		Path dirPath = Paths.get(dir); 
		
		try 
		{
			Files.list(dirPath).forEach(file -> 
			{
				if(!Files.isDirectory(file))
				{
					try 
					{
						Files.delete(file);
					}
					catch(IOException exc)
					{
						System.out.println("Could not delete file: "  +file); 
					}
				}
				
			});
		}
		catch(IOException ex)
		{
			System.out.println("Could not list directory: " + dirPath);
			
		}
	}

}
