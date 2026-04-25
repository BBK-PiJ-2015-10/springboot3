package com.springboot3.example1.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.security.core.userdetails.User;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // return username ->
        //       userRepository.findByUsername(username).asUser();
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build()
        );
        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build()
        );
        return userDetailsManager;
//        userDetailsManager
//                .createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
//        userDetailsManager
//                .createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("ADMIN").build());
//        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()  // Allow H2 console
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")  // Disable CSRF for H2 console
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)  // Allow iframes from same origin
                );

        return http.build();
    }

    //
    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> {
            userManagementRepository.save(new UserAccount("user", "password","USER"));
            userManagementRepository.save(new UserAccount("admin", "password","ADMIN"));
        };
    }

}
