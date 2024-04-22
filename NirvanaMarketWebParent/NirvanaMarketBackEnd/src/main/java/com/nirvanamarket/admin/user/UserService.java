package com.nirvanamarket.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirvanamarket.common.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> listAll() 
	{
		return (List<User>) this.repository.findAll();
	}
}
