package com.springboot3.example1.security;

import jakarta.persistence.criteria.From;
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
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.w3c.dom.css.CSSFontFaceRule;

import java.util.logging.Logger;

@Configuration
public class SecurityConfig {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
       // return username -> userRepository.findByUsername(username).asUser();
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
        logger.info("Returning userDetailsManager");
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/login").permitAll()
                                        //.anyRequest().authenticated()
                                        //.requestMatchers("/", "/search").authenticated()
                                        //.requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                                        //.requestMatchers(HttpMethod.POST, "/new-video", "/api/**").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                        //.anyRequest().denyAll()
                )
                .formLogin(form -> form.defaultSuccessUrl("/", true).permitAll())
                //.httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")  // Disable CSRF for H2 console
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)  // Allow iframes from same origin
                )
                .build();
    }

//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**").permitAll()  // Allow H2 console
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .defaultSuccessUrl("/", true)
//                        .permitAll()
//                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**")  // Disable CSRF for H2 console
//                )
//                .headers(headers -> headers
//                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)  // Allow iframes from same origin
//                );
//        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/h2-console/**").permitAll()
//                .anyRequest().authenticated()
//        );
//        http.formLogin(form -> form.defaultSuccessUrl("/", true).permitAll());
//        //http.httpBasic();
//        http.csrf(csfr -> csfr.ignoringRequestMatchers("/h2-console/**"));
//        http.headers(headers -> headers
//                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//        return http.build();


    //
    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> {
            if (userManagementRepository.count() == 0) {
                userManagementRepository.save(new UserAccount("user", "password", "USER"));
                logger.info("Saved user");
                userManagementRepository.save(new UserAccount("admin", "password", "ADMIN"));
                logger.info("Saved admin");
            }
        };
    }

}
