package com.springboot3.example1.repo;

import com.springboot3.example1.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByNameContainsOrDescriptionContainsIgnoreCase(String name, String description);

    List<VideoEntity> findByNameContainsIgnoreCase(String name);

    List<VideoEntity> findByDescriptionContainsIgnoreCase(String description);
}
