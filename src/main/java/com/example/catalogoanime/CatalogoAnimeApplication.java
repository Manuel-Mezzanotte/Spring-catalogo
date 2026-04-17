package com.example.catalogoanime;

import com.example.catalogoanime.model.AppUser;
import com.example.catalogoanime.repository.AnimeRepository;
import com.example.catalogoanime.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CatalogoAnimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoAnimeApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDb(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				AppUser admin = new AppUser();
				admin.setUsername("admin");
				admin.setEmail("admin@gmail.com");
				admin.setPassword(passwordEncoder.encode("admin"));
				userRepository.save(admin);
				System.out.println("Account 'admin' creato con successo al primo avvio!");
			}
		};
	}
}
