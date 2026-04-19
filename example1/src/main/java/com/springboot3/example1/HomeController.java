package com.springboot3.example1;

import com.springboot3.example1.dto.VideoSearch;
import com.springboot3.example1.entity.VideoEntity;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.dialect.function.ListaggStringAggEmulation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String newVideo(@ModelAttribute Video newVideo) {
        videoService.create(newVideo);
        return "redirect:/";
    }

    @GetMapping("/react")
    public String testing() {
        System.out.println("SOMEONE IS CALLING ME");
        return "react";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearch(@ModelAttribute VideoSearch videoToSearch, Model model) {
        List<VideoEntity> searchResult = videoService.search(videoToSearch);
        logger.info(String.format("Found %d videos", searchResult.size()));
        model.addAttribute("videos", searchResult);
        return "index";
    }
}
