package com.example.pagingapplication.services

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.pagingapplication.model.HackerNews
import com.example.pagingapplication.services.network.hackernews.api.HackerNewsApi
import com.example.pagingapplication.services.paging.HackerNewsTopStoriesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealHackerNewsRepository(
    private val api: HackerNewsApi
) : HackerNewsRepository {

    override fun topStoriesFlow(): Flow<PagingData<HackerNews.Story>> = Pager(
        config = HackerNewsRepository.DEFAULT_PAGING_CONFIG
    ) {
        /**
         * This method gets called every time the pager data gets invalidated so don't
         * create the [PagingSource] elsewhere and pass it in here
         */
        /**
         * This method gets called every time the pager data gets invalidated so don't
         * create the [PagingSource] elsewhere and pass it in here
         */
        HackerNewsTopStoriesPagingSource(
            api
        )
    }
        .flow
        .map {
            /**
             * Filter out all the dead and deleted items
             */
            it.filterSync { item -> !item.dead && !item.deleted }
        }
}