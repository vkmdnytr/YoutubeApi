package com.example.youtube.entity.model

data class YouTubeResponseItem(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)