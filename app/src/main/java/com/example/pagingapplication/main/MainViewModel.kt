package com.example.pagingapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.pagingapplication.services.network.hackernews.HackerNewsRepository
import com.example.pagingapplication.services.network.hackernews.HackerNewsTopStoriesPagingSource
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val repository: HackerNewsRepository
) : ViewModel() {

    private val topStoriesPager = Pager(PagingConfig(pageSize = 10)) {
        /**
         *
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

    class Factory(private val repository: HackerNewsRepository):
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}