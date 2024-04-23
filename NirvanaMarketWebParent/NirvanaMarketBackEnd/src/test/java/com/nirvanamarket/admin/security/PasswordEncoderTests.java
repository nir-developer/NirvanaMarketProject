package com.nirvanamarket.admin.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTests {

	@Test
	void testPasswordEncoder()
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String rawPassword = "superduper100";
		String encodedPassword = encoder.encode(rawPassword);
		
		System.out.println(">>hashed password: " + encodedPassword);
		
		boolean matches = encoder.matches(rawPassword, encodedPassword);
		
		assertThat(matches).isTrue();
		
		
	}
}
