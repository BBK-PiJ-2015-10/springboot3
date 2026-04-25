package com.springboot3.example1.security;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
//
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserAccount(String username, String password, String... roles) {
        this.username = username;
        this.password = password;
        for (String role : roles) {
            Assert.isTrue(!role.startsWith("ROLE_"), () -> role + " cannot start with ROLE_ (it is automatically added)");
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
    }



    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserAccount() {
    }

    public UserDetails asUser() {
        return User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }

}
