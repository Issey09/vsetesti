package org.example.vsetesti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


import java.time.Duration;

@SpringBootApplication
public class VsetestiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VsetestiApplication.class, args);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Разрешить все запросы
            );
        return http.build();
    }



}
