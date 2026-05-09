package com.springboot3.example2.youtube.services;

import com.springboot3.example2.youtube.dtos.SearchListResponse;
import com.springboot3.example2.youtube.dtos.Sort;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface YouTube {

    @GetExchange("/search?part=snippet&type=video")
    SearchListResponse channelVideos(@RequestParam String channelId, @RequestParam int maxResults, @RequestParam Sort order);
}


