package com.springboot3.example2.youtube.dtos;

public record SearchResults(String kind, String etag, SearchId id,SearchSnippet snippet) {
}
