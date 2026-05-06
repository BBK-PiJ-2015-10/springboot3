package com.springboot3.example2.controller;

import com.springboot3.example2.youtube.dtos.Sort;
import com.springboot3.example2.youtube.services.YouTube;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final YouTube youTube;

    public HomeController(YouTube youTube) {
        this.youTube = youTube;
    }

    @GetMapping
    String index(Model model){
        model.addAttribute("channelVideos", youTube.channelVideos("UCjukbYOd6pjrMpNMFAOKYyw",10, Sort.VIEW_COUNT));
        return "index";
    }


}
