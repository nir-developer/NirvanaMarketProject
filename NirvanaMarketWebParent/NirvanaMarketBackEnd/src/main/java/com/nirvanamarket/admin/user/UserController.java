package com.nirvanamarket.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	

}
