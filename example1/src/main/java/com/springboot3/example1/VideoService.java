package com.springboot3.example1;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VideoService {

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

}
