package com.nirvanamarket.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nirvanamarket.common.entity.Role;
import com.nirvanamarket.common.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
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
		
		boolean isUpdatingUser = (user.getId() != null); 
		
		//UPDATING MODE
		if(isUpdatingUser)
		{
			
			//1) GET THE USER FROM DB BY ID
			User existingUser = this.userRepository.findById(user.getId()).get();
				
			//2)IF THE PASSWORD ON THE FORM IS EMPTY - DONT UPDATE THE PASSWORD OF THE DB USER (THE ADMIN WANTS TO KEEP THE USER PASSWORD
			//INSTEAD - UPDATE THE PASSWORD OF THE USER FROM THE FORM -TO THE PASSWORD OF THE EXISTED USER
			if(user.getPassword().isEmpty())
			{
				user.setPassword(existingUser.getPassword());
			}
			
			//3)password of the user in the form is not empty 
			//- then encode the user password form the form 
			//
			else
			{
				encodePassword(user);
				System.err.println(user.getPassword()); 
				//existingUser.setPassword(user.getPassword());
			}
		}
		//NEW MODE - JUST ENCODE THE PASSWORD OF T
		else
		{
			encodePassword(user);
			System.err.println(user.getPassword()); 
			
			
		}
		
		//PERIST THE USER IN THE FORM TO DB TO DB!
		this.userRepository.save(user);
		
	}
	
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id, String email)
	{
		User userByEmail = this.userRepository.getUseByEmail(email);
		
		//CASE 1: No user with the given email exists in DB - return true - good for edit & new 
		if(userByEmail == null) return true; 
		
		//(*)HERE I KNOW THERE IS NO USER WITH THE GIVEN EMAIL!! 
		
		boolean isCreatingNew = (id == null); 
		
		
		//Creating mode!
		if(isCreatingNew)
		{
			//(*)WHY DO I NEED TO CHECK THIS?? - CHECK WITHOUT THIS IF! JUST SEND FALSE!
			if(userByEmail != null) return false;
		}
		//Updating mode!
		else
		{
			if(userByEmail.getId() != id)
			{
				return false; 
			}
			
		}
		
		return true; 
		
	}
	
	
	//THE JPA REPO MAY THROW! -> I need to handle it here!
	public User get(Integer id) throws UserNotFoundException
	{
		
		try
		{
			return this.userRepository.findById(id).get();
			
		}
		catch(NoSuchElementException ex)
		{
			throw new UserNotFoundException("Could not find any user with ID: " + id);
		}
	}
	
	//THE JPA REPO DOES NOT THROW! -> I need THROW - AND NOT HANDLE IT!
	//IMPORTANT: FOR PERFORMANCE - FOR OLN CHECK EXISTENCE OF USER : -call  my countById() method -instead of the JPA findById() !
	public void delete(Integer id) throws UserNotFoundException
	{
		
		Long countById = this.userRepository.countById(id);
		
		if(countById == null || countById == 0)
		{
			throw new UserNotFoundException("Could not find any user with ID: " + id);

		}
		
		this.userRepository.deleteById(id);
	}
	
}
