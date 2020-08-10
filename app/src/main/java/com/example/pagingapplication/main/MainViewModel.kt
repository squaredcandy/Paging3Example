package com.example.pagingapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.pagingapplication.services.database.hackernews.HackerNewsTopStoriesRemoteMediator
import com.example.pagingapplication.services.network.hackernews.HackerNewsRepository
import com.example.pagingapplication.services.network.hackernews.HackerNewsTopStoriesPagingSource
import com.example.pagingapplication.model.HackerNews
import com.example.pagingapplication.model.ItemType
import com.example.pagingapplication.model.toHackerNewsItem
import com.example.pagingapplication.services.database.Database
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val repository: HackerNewsRepository,
    private val database: Database
) : ViewModel() {

    @ExperimentalPagingApi
    private val topStoriesDatabasePager = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = HackerNewsTopStoriesRemoteMediator(repository, database)
    ) {
        /**
         * This method gets called every time the pager data gets invalidated so don't
         * create the [PagingSource] elsewhere and pass it in here
         */
        database.hackerNewsDao().getPagingSource()
    }
    @ExperimentalPagingApi
    val topStoriesDatabaseFlow = topStoriesDatabasePager.flow
        .map { pagingData ->
            pagingData.filterSync { it.itemType == ItemType.Story }
                .map { it.toHackerNewsItem() as HackerNews.Story }
                .filterSync { item -> !item.dead && !item.deleted }
        }

    private val topStoriesPager = Pager(PagingConfig(pageSize = 10)) {
        /**
         * This method gets called every time the pager data gets invalidated so don't
         * create the [PagingSource] elsewhere and pass it in here
         */
        HackerNewsTopStoriesPagingSource(repository)
    }
    val topStoriesFlow = topStoriesPager.flow
        .map {
            /**
             * Filter out all the dead and deleted items
             */
            it.filterSync { item -> !item.dead && !item.deleted }
        }
        .cachedIn(viewModelScope)

    class Factory(
        private val repository: HackerNewsRepository,
        private val database: Database
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository, database) as T
        }
    }
}