package com.example.youtube.model.rest

import com.example.youtube.model.entities.YouTubeResponseItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface YoutubeService {
    //    https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&regionCode=US&key=AIzaSyCpDASWd6vMXAberXZKx7yPogYiB4zJGSU
    @GET(YoutubeServiceUrls.YOUTUBE_URL)
    fun getPopularYouTubeListAsync(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("chart") chart: String = "mostPopular",
        @Query("regionCode") regionCode: String,
        @Query("key") key: String = "AIzaSyCpDASWd6vMXAberXZKx7yPogYiB4zJGSU"
    ): Deferred<Response<YouTubeResponseItem>>
}
