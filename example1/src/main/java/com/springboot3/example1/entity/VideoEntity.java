package com.springboot3.example1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class VideoEntity {

    private @Id @GeneratedValue Long id;
    private String name;
    private String description;

    public VideoEntity() {
        this(null,null);
    };

    public VideoEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
