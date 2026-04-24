package com.springboot3.example1.repo;

import com.springboot3.example1.entity.VideoEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    List<VideoEntity> findByNameContainsOrDescriptionContainsIgnoreCase(String name, String description, Sort sort);

    List<VideoEntity> findByNameContainsIgnoreCase(String name, Sort sort);

    List<VideoEntity> findByDescriptionContainsIgnoreCase(String description, Sort sort);

    @Query(value = "select * from VIDEO_ENTITY WHERE NAME = ?1",nativeQuery = true)
    List<VideoEntity> findCustomWithPureSql(String name);

}
