package com.springboot3.example2.youtube.dtos;

import java.util.Map;

public record SearchSnippet(String publishedAt, String channelId, String title, String description,
                            Map<String, SearchThumbnail> thumbnails, String channelTitle) {
}
