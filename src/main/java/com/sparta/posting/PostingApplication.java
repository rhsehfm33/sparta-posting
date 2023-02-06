package com.sparta.posting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PostingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostingApplication.class, args);
	}

}
