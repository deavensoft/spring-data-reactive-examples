package com.example.reactivedataaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class ReactiveDataAccessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveDataAccessApplication.class, args);
	}

//	static {
//		BlockHound.install();
//	}
}
