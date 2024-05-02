package com.nirvanamarket.admin.user;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.util.StringUtil;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nirvanamarket.admin.FileUploadUtil;
import com.nirvanamarket.common.entity.Role;
import com.nirvanamarket.common.entity.User;

import jakarta.websocket.server.PathParam;


@Controller
public class UserController {
	
	@Autowired
	private UserService service; 
	
	@GetMapping("/users")
	public String listAll(Model model) 
	{
		List<User> listUsers = this.service.listAll();
		//System.err.println(listUsers);
		model.addAttribute("listUsers", listUsers); 
		//LOGICAL VIEW NAME - SB WILL CONVERT IT TO THE PHISICAL LOCATION OF THE HTML TEMPLATES
		return "users"; 
	}
	
	
	@GetMapping("/users/new")
	public String newUser(Model model)
	{
		//TO POPULATE THE FORM WITH ALL ROLES (LIST OF RADIO BUTTONS) 
		List<Role> listRoles = this.service.listRoles();
		
		//ENABLE THE USER 
		User user = new User(); 
		user.setEnabled(true);
		
		//UPDATE THE MODEL
		model.addAttribute("pageTitle", "Create New User"); 
		model.addAttribute("user", user );
		model.addAttribute("listRoles", listRoles);
		
		return "user_form";
	}
	
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes, 
			@RequestParam("image") MultipartFile multipartFile) throws IOException
	{
		
		System.err.println("POST SAVE: the user from the form : "); 
		System.err.println(user);
		
	
		//FIRST BRANCH OF THE UPLOAD PHOTO LOGIC(see presentation): USER UPLOAD A PHOTO IN THE FORM
		if(!multipartFile.isEmpty())
		{
			
			System.err.println("POST HANDLER - saveUser: MULTIPART IS NOT EMPTY  FILE NAME UPLOADED: " + multipartFile.getOriginalFilename());
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = this.service.save(user);
			
			
			String uploadDir = "user-photos/" + savedUser.getId() ; 
			
			//CLEAN DIRECOTRY BEFORE SAVING THE FILE - TO PREVENT ADDING MORE THAN ONE PHOTO PER USER - WHEN EDITING!
			FileUploadUtil.cleanDir(uploadDir);			
			
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		//SECOND BRANCH OF THE UPLOAD PHOTO LOGIC(see presentation): USER DID NOTUPLOAD A PHOTO IN THE FORM
		//NOTE: THE VALUE OF THE photos is stored in an hidden input field inside the form-group of the impage  - in the user_form.html
		else 
		{
			//T.H (CHAD) => The is bound to the form ! - user.getPhotos.isEmpty() => user has no photo(WHICH I SET AS AN HIDDEN INPUT)
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			service.save(user);
			
			
		}

	
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		return "redirect:/users"; 
		
		
		//TEST THE ORIGINAL FILE NAME UPLOADED BY THE CLIENT 
		//OK: Screenshot from 2022-05-06 17-43-18.png
		//System.err.println(multipartFile.getOriginalFilename());
		
		
		//IN THE BEGINING OF THIS SECTION :WHEN TESTING THE FILE INPUT COMMENT OUT THE PERSISTENCE!
		//this.service.save(user);
//		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		
	}
	
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable (name = "id") Integer id, Model model, RedirectAttributes redirectAttributes)
	{
		try 
		{
			User user = this.service.get(id); 
			List<Role> listRoles = this.service.listRoles();

			
			
			model.addAttribute("pageTitle", "Edit User (ID: " + user.getId() + ")"); 
			model.addAttribute("listRoles", listRoles); 
			model.addAttribute("user", user);
		
			
			return "user_form"; 
			
		}
		catch(UserNotFoundException ex)
		{
			
			redirectAttributes.addFlashAttribute("message", ex.getMessage());

			return "redirect:/users"; 
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable (name = "id") Integer id, Model model, RedirectAttributes redirectAttributes)
	{
		try 
		{
			this.service.delete(id);
			
			redirectAttributes.addFlashAttribute("message", 
					"The user ID " + id + " has been deleted successfully"); 
			
		}
		catch(UserNotFoundException ex)
		{
			
			redirectAttributes.addFlashAttribute("message", ex.getMessage());

			//return "redirect:/users"; 
		}
		
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String enableUserEnabledStatus(@PathVariable(name="id")Integer id,
			@PathVariable(name="status") boolean enabled,
			RedirectAttributes redirectAttributes) 
	{
		
		this.service.updateUserEnabledStatus(id, enabled);
		
		//IMPORTANT  - Toggle the status!
		String status = enabled ? "disabled" : "enabled"; 
		String messgae = "The user ID " + id + " has been " + status; 
		
		
		
		//Redirect to listing page 
		redirectAttributes.addFlashAttribute("message", messgae);
		return "redirect:/users";

		
		
		//return null; 
	}
	

}
