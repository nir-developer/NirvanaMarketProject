package com.nirvanamarket.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		System.err.println(listUsers);
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
	public String saveUser(User user, RedirectAttributes redirectAttributes)
	{
		this.service.save(user);
		
				
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		return "redirect:/users"; 
	}
	
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable (name = "id") Integer id, Model model, RedirectAttributes redirectAttributes)
	{
		try 
		{
			User user = this.service.get(id); 
			List<Role> listRoles = this.service.listRoles();

			
			//UPDATE THE MODEL
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
	

}
