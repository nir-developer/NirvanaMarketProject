package com.nirvanamarket.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nirvanamarket.common.entity.Role;
import com.nirvanamarket.common.entity.User;


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
	
	
	

}
