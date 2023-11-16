package com.sprintpay.projetsig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetSigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetSigApplication.class, args);
	}

	/*@Bean
	CommandLineRunner start(AppUserService appUserService){
		return args -> {
			//accountService.addNewRole(new AppRole(null,"USER"));
			//accountService.addNewRole(new AppRole(null,"ADMIN"));

			//accountService.addNewUser(new AppUser(null,"user1","1234", new ArrayList<>()));
			//accountService.addNewUser(new AppUser(null,"admin","1234", new ArrayList<>()));

			//accountService.addRoleToUser("user1","USER");
			//accountService.addRoleToUser("admin","ADMIN");
		};
	}*/

}
