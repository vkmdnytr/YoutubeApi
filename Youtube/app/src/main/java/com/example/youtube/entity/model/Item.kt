package com.example.youtube.entity.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Item(
    val contentDetails: ContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet,
    val statistics: Statistics
):Serializable