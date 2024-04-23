package com.nirvanamarket.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvanamarket.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer > {

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUseByEmail(@Param("email") String email);
}
