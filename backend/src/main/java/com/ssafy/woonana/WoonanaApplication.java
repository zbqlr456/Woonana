package com.ssafy.woonana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

// http://localhost:8081/swagger-ui/index.html
// http://localhost:8081/chat/room

@EnableJpaAuditing
@SpringBootApplication
public class WoonanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoonanaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of(UUID.randomUUID().toString());
			}
		};

//		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
