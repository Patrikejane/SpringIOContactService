package com.loollablk.contactservice;

import com.loollablk.contactservice.model.UserInfo;
import com.loollablk.contactservice.model.UserRole;
import com.loollablk.contactservice.repository.UserInfoRepository;
import com.loollablk.contactservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ContactServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactServiceApplication.class, args);

		String password = "Abc123";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode(password);
		System.out.println("Hashed Password: " + hashedPassword);

	}


	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Bean
	public CommandLineRunner setUpDb() {

		return args -> {
			System.out.println("Command Line Runner !");

			Set<UserRole> adminRoles = new HashSet <> (  );
			UserRole adminRole = new UserRole (1,"ADMIN" );
			adminRoles.add (adminRole);

			Set<UserRole> userRoles = new HashSet <> (  );
			UserRole userRole = new UserRole (2,"USER" ) ;
			userRoles.add (userRole);

			userRoleRepository.save ( adminRole );
			userRoleRepository.save ( userRole  );

			userInfoRepository.save (
					new UserInfo ( 1,"XXXXX","$2a$10$fNd453z8wiMWPqBqfw33bOk3w1lXEboDXYKH7BF90hJkVaaJI90uO", adminRoles));

			userInfoRepository.save (
					new UserInfo ( 2,"YYYYYYY","$2a$10$fNd453z8wiMWPqBqfw33bOk3w1lXEboDXYKH7BF90hJkVaaJI90uO", userRoles));

		};

	}





}
