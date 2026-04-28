package com.springboot3.example1.service;

import com.springboot3.example1.dto.Video;
import com.springboot3.example1.dto.UniversalSearch;
import com.springboot3.example1.dto.VideoSearch;
import com.springboot3.example1.entity.VideoEntity;
import com.springboot3.example1.repo.VideoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.logging.Logger;

@Service
public class VideoService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private VideoRepository videoRepository;


    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    private void loadVideos() {
        List<VideoEntity> databaseVideos = List.of(new VideoEntity("cat1", "pet movies", "alice"), new VideoEntity("dog1", "pet movies", "bob"), new VideoEntity("cat2", "pet movies", "bob"), new VideoEntity("superman1", "human movies", "alice"));
        videoRepository.saveAll(databaseVideos);
    }

    public List<VideoEntity> getVideos() {
        return videoRepository.findAll();
    }

    public void delete(Long videoId) {
        videoRepository.deleteById(videoId);
    }

    public Video create(Video newVideo, String userName) {
        videoRepository.save(new VideoEntity(newVideo.name(), "undeclared", userName));
        //videoRepository.saveAndFlush()
        return newVideo;
    }

    public List<VideoEntity> search(VideoSearch videoSearch) {
        Sort sort = Sort.by("name").ascending().and(Sort.by("description").descending());
        if (StringUtils.hasText(videoSearch.name()) && StringUtils.hasText(videoSearch.description())) {
            logger.info(String.format("Searching by name %s and description %s", videoSearch.name(), videoSearch.description()));
            return videoRepository.findByNameContainsOrDescriptionContainsIgnoreCase(videoSearch.name(), videoSearch.description(), sort);
        }
        if (StringUtils.hasText(videoSearch.name())) {
            return videoRepository.findByNameContainsIgnoreCase(videoSearch.name(), sort);
        }
        if (StringUtils.hasText(videoSearch.description())) {
            return videoRepository.findByDescriptionContainsIgnoreCase(videoSearch.description(), sort);
        }
        return List.of();
    }

    public List<VideoEntity> search(UniversalSearch search) {
        VideoEntity probe = new VideoEntity();
        if (StringUtils.hasText(search.value())) {
            probe.setName(search.value());
            probe.setDescription(search.value());
        }
        Example<VideoEntity> example = Example.of(probe, ExampleMatcher.matchingAny().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return videoRepository.findAll(example);
    }

}
