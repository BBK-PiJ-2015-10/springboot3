package com.springboot3.example1;

import com.springboot3.example1.dto.VideoSearch;
import com.springboot3.example1.entity.VideoEntity;
import com.springboot3.example1.repo.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VideoService {

    private VideoRepository videoRepository;


    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    private List<Video> videos = new CopyOnWriteArrayList<>(List.of(
            new Video("cat1"),
            new Video("cat2"),
            new Video("cat3")
    ));

    public List<Video> getVideos() {
        return videos;
    }

    public Video create(Video newVideo) {
        videos.add(newVideo);
        return newVideo;
    }

    public List<VideoEntity> search(VideoSearch videoSearch) {
        if (StringUtils.hasText(videoSearch.name()) && StringUtils.hasText(videoSearch.description())) {
            return videoRepository.findByNameContainsOrDescriptionContainsIgnoreCase(videoSearch.name(), videoSearch.description());
        }
        if (StringUtils.hasText(videoSearch.name())) {
            return videoRepository.findByNameContainsIgnoreCase(videoSearch.name());
        }
        if (StringUtils.hasText(videoSearch.description())) {
            return videoRepository.findByDescriptionContainsIgnoreCase(videoSearch.description());
        }
        return List.of();
    }

}
