package com.springboot3.example2.controller;

import com.springboot3.example2.youtube.dtos.Sort;
import com.springboot3.example2.youtube.services.YouTube;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class HomeController {

    private final YouTube youTube;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public HomeController(YouTube youTube) {
        this.youTube = youTube;
    }

    @GetMapping
    String index(Model model){
        logger.info("Someone is calling get endpoint");
        var videos = youTube.channelVideos("UCjukbYOd6pjrMpNMFAOKYyw",10, Sort.VIEW_COUNT);
        logger.info(String.format("I fetched %d videos",videos.items().length));
        model.addAttribute("videos",videos);
        logger.info("Someone called get endpoint");
        return "index";
    }


}
