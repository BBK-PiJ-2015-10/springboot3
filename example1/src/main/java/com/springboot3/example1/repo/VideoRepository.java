package com.springboot3.example1.repo;

import com.springboot3.example1.entity.VideoEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByNameContainsOrDescriptionContainsIgnoreCase(String name, String description, Sort sort);

    List<VideoEntity> findByNameContainsIgnoreCase(String name, Sort sort);

    List<VideoEntity> findByDescriptionContainsIgnoreCase(String description, Sort sort);
}
