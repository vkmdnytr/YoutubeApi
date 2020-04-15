package com.example.youtube.model.rest

import com.example.youtube.model.rest.YoutubeServiceUrls.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getService(): YoutubeService =getRetrofit().create(YoutubeService::class.java)

fun getRetrofit():Retrofit{
    val okHttpClient=OkHttpProvider.instance
    return  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
}