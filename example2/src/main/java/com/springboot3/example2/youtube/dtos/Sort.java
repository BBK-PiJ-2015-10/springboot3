package com.springboot3.example2.youtube.dtos;

import io.netty.util.concurrent.ThreadPerTaskExecutor;

public enum Sort {
    DATE("date"),
    VIEW_COUNT("viewCount"),
    TITLE("title"),
    RATING("rating")
    ;

    private final String type;

    Sort(String type) {
        this.type = type;
    }

}
