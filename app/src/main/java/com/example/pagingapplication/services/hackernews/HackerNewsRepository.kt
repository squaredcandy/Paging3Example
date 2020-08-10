package com.example.pagingapplication.services.hackernews

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingapplication.model.HackerNews
import kotlinx.coroutines.flow.Flow

interface HackerNewsRepository {
    fun topStoriesFlow(): Flow<PagingData<HackerNews.Story>>

    companion object {
        val DEFAULT_PAGING_CONFIG = PagingConfig(pageSize = 10)
    }
}
