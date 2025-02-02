package com.e_commerce_product_catalog_api.setup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.e_commerce_product_catalog_api.repository.UserRepository;
import com.e_commerce_product_catalog_api.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

@Configuration
public class UserDataInitializer {

    @Bean
    public CommandLineRunner initializeData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create sample users
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Password is encoded
                admin.setRoles(Set.of("ROLE_ADMIN", "ROLE_USER")); // Multiple roles
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123")); // Password is encoded
                user.setRoles(Set.of("ROLE_USER")); // Single role
                userRepository.save(user);
            }

            System.out.println("Sample users initialized!");
        };
    }

}
