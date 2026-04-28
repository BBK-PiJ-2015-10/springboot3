package com.springboot3.example1.controller;

import com.springboot3.example1.dto.Video;
import com.springboot3.example1.dto.UniversalSearch;
import com.springboot3.example1.dto.VideoSearch;
import com.springboot3.example1.entity.VideoEntity;
import com.springboot3.example1.service.VideoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private VideoService videoService;

    public HomeController(VideoService videoService) {
        this.videoService = videoService;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videoService.getVideos());
        return "index";
    }

    @PostMapping("/new-video")
    public String newVideo(@ModelAttribute Video newVideo, Authentication authentication) {
        logger.info(String.format("Received request to create"));
        videoService.create(newVideo, authentication.getName());
        return "redirect:/";
    }

    @PostMapping("/delete/videos/{videoId}")
    public String deleteVideo(@PathVariable Long videoId) {
        logger.info(String.format("Received request to delete video with id %d",videoId));
        videoService.delete(videoId);
        logger.info(String.format("Deleted video with id %d",videoId));
        return "redirect:/";
    }

    @GetMapping("/react")
    public String testing() {
        System.out.println("SOMEONE IS CALLING ME");
        return "react";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearch(@ModelAttribute VideoSearch videoToSearch, Model model) {
        logger.info(String.format("Received request to search by multi-fiend"));
        List<VideoEntity> searchResult = videoService.search(videoToSearch);
        logger.info(String.format("Found %d videos", searchResult.size()));
        model.addAttribute("videos", searchResult);
        return "index";
    }

    @PostMapping("/universal-search")
    public String universalSearch(@ModelAttribute UniversalSearch search, Model model) {
        logger.info(String.format("Received request to search by universal"));
        var foundVideos = videoService.search(search);
        model.addAttribute("videos", foundVideos);
        return "index";
    }

}
