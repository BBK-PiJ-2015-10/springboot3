package com.springboot3.example1.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.security.core.userdetails.User;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.logging.Logger;

@Configuration
public class SecurityConfig {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username).asUser();
//        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build()
//        );
//        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build()
//        );
//        logger.info("Returning userDetailsManager");
//        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/login").permitAll()
                                .requestMatchers("/").authenticated()
                                .requestMatchers(HttpMethod.POST, "/multi-field-search").authenticated()
                                .requestMatchers(HttpMethod.POST, "/universal-search").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                                .requestMatchers(HttpMethod.POST,"/delete/videos/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/new-video", "/api/**").hasRole("ADMIN")
                                .requestMatchers("/h2-console/**").authenticated()
                                //.anyRequest().authenticated()
                                .anyRequest().denyAll()
                )
                .formLogin(form -> form.defaultSuccessUrl("/", true).permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")  // Disable CSRF for H2 console
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)  // Allow iframes from same origin
                )
                .build();
    }

    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> {
            if (userManagementRepository.count() == 0) {
                userManagementRepository.save(new UserAccount("alice", "password", "USER"));
                logger.info("Saved user");
                userManagementRepository.save(new UserAccount("bob", "password", "USER"));
                logger.info("Saved user");
                userManagementRepository.save(new UserAccount("admin", "password", "ADMIN"));
                logger.info("Saved admin");
            }
        };
    }

}
