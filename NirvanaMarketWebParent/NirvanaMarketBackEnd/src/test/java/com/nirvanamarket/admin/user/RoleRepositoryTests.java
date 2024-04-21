package com.nirvanamarket.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.nirvanamarket.common.entity.Role;

//IMPORTANT - RUN THE 2 TEST METHODS ONLY ONCE - FOR CREATING ROLES 
//AND THEN ADD THE @Disable ON EACH - OTHERWISE - FAILED - SINCE UNQIUE ROLES!!

@DataJpaTest
//TO PREVENT THE DEFAULT OF RUNNING AGAINAST THE IN-MEMORY DB! SINCE I WNAT TO RUN THE REAL DB
@AutoConfigureTestDatabase(replace = Replace.NONE)
////TO UPDATE THE DATA - TO COMMIT THE CHANGE INTO THE DB
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Disabled
	@DisplayName("Test Create Admin Role")
	@Test
	void testCreateRole()
	{
		//WHEN
		Role role = new Role("Admin","manage everything");
		
		Role savedRole = this.roleRepository.save(role); 
		
		
		//ASSERTJ library(assertThat) instead of the JUNIT default assert() method!
		assertThat(savedRole).isNotNull();
		assertThat(savedRole.getId() > 0).isTrue();
		
	}
	
	
	@Disabled
	@DisplayName("Test Create Rest Roles")
	@Test
	void testCreateRestRoles()
	{
		//WHEN
		Role roleSalesPerson = new Role("Salesperson","manage product price,customers, shipping, orders and sales report");
		Role roleEditor = new Role("Editor","manage categories, brands, articles and menus");
		Role roleShipper = new Role("Shipper","view product, view orders, and update order status");
		Role roleAssistant = new Role("Assitant","manage questions and reviews");

		
		//PERSISTE AT ONCE (Returns an IMMUTABLE LIST
		this.roleRepository.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
		
	
	}
	

}
