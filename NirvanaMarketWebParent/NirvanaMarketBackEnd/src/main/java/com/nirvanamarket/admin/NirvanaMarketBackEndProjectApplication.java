package com.nirvanamarket.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.nirvanamarket.common.entity", "com.nirvanamarket.admin.user"})
public class NirvanaMarketBackEndProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NirvanaMarketBackEndProjectApplication.class, args);
	}

}
