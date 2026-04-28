package com.springboot3.example1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class VideoEntity {

    private @Id @GeneratedValue Long id;
    private String username;
    private String name;
    private String description;

    public VideoEntity() {
        this(null,null,null);
    };

    public VideoEntity(String name, String description,String username) {
        this.name = name;
        this.description = description;
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
