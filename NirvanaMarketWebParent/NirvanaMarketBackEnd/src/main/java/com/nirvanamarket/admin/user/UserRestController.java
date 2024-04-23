package com.nirvanamarket.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
	
	@Autowired
	private UserService service; 
	
	
	//For simplicity - return String - not JSON
	//THE URL WILL BE CALLED BY AJAX POST REQUEST WHEN SUBMITTING THE 
	//SIGNUP FORM BY CLICKING THE Save button (input type="submit")
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(String email)
	{
		boolean isUnique = this.service.isEmailUnique(email);
		System.out.println("CONTROLLER - THE USER IS UNIQUE ? " + isUnique); 
		return this.service.isEmailUnique(email) ? "OK":"Duplicated";
	}

}
