package com.nirvanamarket.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirvanamarket.common.entity.Role;
import com.nirvanamarket.common.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	public List<User> listAll() 
	{
		return (List<User>) this.userRepository.findAll();
	}
	
	
	//TO POPULATE THE FORM THAT CREATE NEW USER - WITH ALL ROLES IN DB
	public List<Role> listRoles() 
	{
		return (List<Role>)this.roleRepository.findAll();
		
	}


	public void save(User user) {
		
		this.userRepository.save(user);
		
	}
	
	
}
