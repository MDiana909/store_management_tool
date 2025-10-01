package com.dvm.store_management_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class StoreManagementToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreManagementToolApplication.class, args);
	}

}
