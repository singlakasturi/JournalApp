package com.firstP.jour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//Rest API - Representational State Transfer Application Programing Interfaces

@SpringBootApplication
@EnableTransactionManagement
public class FirstPApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstPApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory databaseFactory) {
		return new MongoTransactionManager(databaseFactory);
	}

}
