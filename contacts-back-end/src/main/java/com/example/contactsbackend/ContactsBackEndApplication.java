package com.example.contactsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描mapper
@MapperScan("com.example.contactsbackend.mapper")
public class ContactsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactsBackEndApplication.class, args);
	}

}
