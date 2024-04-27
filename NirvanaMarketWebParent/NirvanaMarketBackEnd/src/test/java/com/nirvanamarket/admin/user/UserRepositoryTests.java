package com.nirvanamarket.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.nirvanamarket.common.entity.Role;
import com.nirvanamarket.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	
	@Autowired
	private UserRepository repository;
	
	//FOR GETTING SOME  ROLES FROM DB
	@Autowired
	private TestEntityManager entityManager; 
	
	
	
	//EMPTY TABLE - FOR CREATING THE TABLE
	@Disabled
	@Test
	public void testCreateUserWithOneRole()
	{
		Role roleAdmin = entityManager.find(Role.class, 1);
		
		User userNir = new User("niritzhak@gmail.com", "superduper100", "Nir", "Itzhak");
		userNir.addRole(roleAdmin);
		
		//WHEN
		User savedUser = this.repository.save(userNir);
		
		assertThat(savedUser).isNotNull(); 
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Disabled
	@DisplayName("create user with 2 roles")
	@Test
	public void testCreateUserWithTwoRoles()
	{
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumer");
		Role roleEditor = new Role(3); 
		Role roleAssistant =new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		///WHEN
		User savedUser = repository.save(userRavi);
		
		//ASSERT 
		assertThat(savedUser.getId()).isGreaterThan(0);
		
		System.out.println(savedUser);
		
	}
	
	//User [id=1, email=niritzhak@gmail.com, password=superduper100, firstName=Nir, lastName=Itzhak, photos=null, enabled=false, roles=[Role [id=1, name=Admin, description=manage everything]]]
	@Test
	@Disabled
	void testListAllUsers()
	{
		Iterable<User> listUsers = this.repository.findAll();
		
		
		assertThat(listUsers).isNotNull(); 
		
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	@Disabled
	void testGetUserByIdWhenFound()
	{
		
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumer");
		userRavi.setId(2);
		Role roleEditor = new Role(3); 
		Role roleAssistant =new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		
		int id = 2; 
		Optional<User> user = this.repository.findById(id);
		
		assertThat(user.isPresent()).isTrue();
		assertThat(user.get()).isEqualTo(userRavi);
		
		System.out.println(user);
	}
	
	
	@Test
	@Disabled
	void testUpdateUser()
	{
		int id = 2; 
		User userRavi = this.repository.findById(id).get();
		userRavi.setEnabled(true);
		userRavi.setEmail("XXX");
		
		
		//WHEN
		User updatedUser = this.repository.save(userRavi);
		
		assertThat(updatedUser.getEmail()).isEqualTo("XXX");
		assertThat(updatedUser.isEnabled()).isTrue();
		
		System.out.println(updatedUser);
		
		
	}
	
	
	/**
	 * USER WITH ID 2 BEFORE HAS ROLE ID 3,5:
	 * 
	 * mysql> SELECT * FROM users_roles WHERE user_id = 2; 
		+---------+---------+
		| user_id | role_id |
		+---------+---------+
		|       2 |       3 |
		|       2 |       5 |
		+---------+---------+
		
		
		
		USER WITH ID 2 BEFORE HAS ROLE ID 3,5:
				
		mysql> SELECT * FROM users_roles WHERE user_id = 2;
		+---------+---------+
		| user_id | role_id |
		+---------+---------+
		|       2 |       2 |
		|       2 |       5 |
		+---------+---------+
2 rows in set (0.00 sec)
	 */
	@Disabled
	@Test
	void testUpdateUserRoles()
	{
		int id = 2; 
		User userRavi = this.repository.findById(id).get();
		Role roleEditor = new Role(3);
		Role roleSalesPerson = new Role(2);
		
		//CURRENT USER HAS 2 ROLES : Editor and Assitant (ids : 3,5) - AND ADD IT THE Sale Person role (id 2) 
		//REMOVED FROM THE SET BY ID -  IS POSSIBLE ONLY BECASE I IMPLENETED THE HASH AND EQUAL METHODS IN THE ROLE CLASS
		userRavi.getRoles().remove(roleEditor); 
		userRavi.getRoles().add(roleSalesPerson);
		
		User updatedUser = this.repository.save(userRavi); 
		
	
	}
	
	@Disabled
	@Test
	void testDeleteUserById()
	{
		int id = 1; 
		
		this.repository.deleteById(id);
		
//		assertThatThrownBy(()-> {
//			this.repository.findById(id); 
//		}).isInstanceOf(NoSuchElementException.class).hasMessage("Can not find");
//		
		
	}
	
	
	@Test
	void testGetUserByEmailWhenNotFoundShouldReturnNull()
	{
		String email = "abc@def"; 
		
		User user = this.repository.getUseByEmail(email);
		
		assertThat(user).isNull();
		
		System.out.println(user);
	}
	
	@Test
	void testGetUserByEmailWhenFoundShouldReturnNotNull()
	{
		String email = "niritzhak@gmail.com"; 
		
		User user = this.repository.getUseByEmail(email);
			
		assertThat(user).isNotNull();
		
		System.out.println(user);
	}
	
	//OK
	@Test
	void testCountByIdWhenUserNotFoundShouldReturnNull()
	{
		Integer id = 100; 
		//Long expected = 0l;
		
		Long actual = this.repository.countById(id);
		
		assertThat(actual).isNotNull().isEqualTo(0l);
	}
	
	//OK!
	@Test
	void testCountByIdWhenUserFoundShouldReturnOne()
	{
		Integer id = 1; 
		
		
		Long actual = this.repository.countById(id);
		
		assertThat(actual).isNotNull().isGreaterThan(0).isEqualTo(1);
	}


}
