package com.nirvanamarket.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nirvanamarket.common.entity.Role;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Integer> {
	
}
