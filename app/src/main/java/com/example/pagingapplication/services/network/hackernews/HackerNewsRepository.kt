package com.example.pagingapplication.services.network.hackernews

class HackerNewsRepository(
    private val api: HackerNewsApi
) {
    suspend fun getItem(itemId: Int): HackerNewsItemResult {
        return api.getItem(itemId)
    }
    suspend fun getTopStoryIndices(): List<Int> {
        return api.getTopStories()
    }
}