package com.example.pagingapplication.services

import androidx.paging.*
import com.example.pagingapplication.model.HackerNews
import kotlinx.coroutines.flow.Flow

interface HackerNewsRepository {
    fun topStoriesFlow(): Flow<PagingData<HackerNews.Story>>

    companion object {
        val DEFAULT_PAGING_CONFIG = PagingConfig(pageSize = 10)
    }
}

