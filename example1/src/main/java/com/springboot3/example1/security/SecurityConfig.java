package com.springboot3.example1.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.javapoet.LordOfTheStrings;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class SecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository) {

    /// /        return username ->
    /// /                userRepository.findByUsername(username)
    /// /                        .asUser();
//        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager
//                .createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
//        userDetailsManager
//                .createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("ADMIN").build());
//        return userDetailsManager;
//    }
//
    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> {
            userManagementRepository.save(new UserAccount("user", "password"));
            userManagementRepository.save(new UserAccount("admin", "password"));
        };
    }

}
