package com.springboot3.example2.youtube.dtos;

public record SearchListResponse(String kind, String etag, String nextPageToken, String prevPageToken,
                                 PageInfo pageInfo, SearchResults[] items) {
}
