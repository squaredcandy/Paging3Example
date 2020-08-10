package com.example.pagingapplication.services.hackernews

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.pagingapplication.model.HackerNews
import com.example.pagingapplication.model.ItemType
import com.example.pagingapplication.model.toHackerNewsItem
import com.example.pagingapplication.services.database.HackerNewsDatabase
import com.example.pagingapplication.services.hackernews.HackerNewsRepository
import com.example.pagingapplication.services.network.hackernews.api.HackerNewsApi
import com.example.pagingapplication.services.paging.HackerNewsTopStoriesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealCachedHackerNewsRepository(
    private val api: HackerNewsApi,
    private val database: HackerNewsDatabase
) : HackerNewsRepository {

    @ExperimentalPagingApi
    override fun topStoriesFlow(): Flow<PagingData<HackerNews.Story>> = Pager(
        config = HackerNewsRepository.DEFAULT_PAGING_CONFIG,
        remoteMediator = HackerNewsTopStoriesRemoteMediator(
            api,
            database
        )
    ) {
        /**
         * This method gets called every time the pager data gets invalidated so don't
         * create the [PagingSource] elsewhere and pass it in here
         */
        database.hackerNewsDao().getPagingSource()
    }
        .flow
        .map { pagingData ->
            pagingData.filterSync { it.itemType == ItemType.Story }
                .map { it.toHackerNewsItem() as HackerNews.Story }
                .filterSync { item -> !item.dead && !item.deleted }
        }
}