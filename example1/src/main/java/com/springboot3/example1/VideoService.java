package com.springboot3.example1;

import com.springboot3.example1.dto.VideoSearch;
import com.springboot3.example1.entity.VideoEntity;
import com.springboot3.example1.repo.VideoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
        List<VideoEntity> databaseVideos = List.of(
                new VideoEntity("cat1", "pet movies"),
                new VideoEntity("dog1", "pet movies"),
                new VideoEntity("cat2", "pet movies"),
                new VideoEntity("superman1", "human movies")
        );
        videoRepository.saveAll(databaseVideos);
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

}
