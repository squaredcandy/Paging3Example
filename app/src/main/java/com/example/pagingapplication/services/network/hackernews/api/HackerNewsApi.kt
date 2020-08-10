package com.example.pagingapplication.services.network.hackernews

import com.example.pagingapplication.services.network.hackernews.model.HackerNewsItemResult
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApi {

    @GET("/v0/item/{itemId}.json")
    suspend fun getItem(@Path("itemId") itemId: Int): HackerNewsItemResult

    @GET("/v0/topstories.json")
    suspend fun getTopStories(): List<Int>
}