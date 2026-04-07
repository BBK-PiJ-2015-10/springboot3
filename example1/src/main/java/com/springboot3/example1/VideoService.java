package com.springboot3.example1;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private List<Video> videos = List.of(
            new Video("cat1"),
            new Video("cat2"),
            new Video("cat3")
    );

    public List<Video> getVideos() {
        return videos;
    }
}
