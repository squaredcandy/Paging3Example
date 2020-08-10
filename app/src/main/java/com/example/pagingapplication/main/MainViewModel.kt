package com.example.pagingapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.pagingapplication.services.hackernews.HackerNewsRepository

class MainViewModel(
    private val repository: HackerNewsRepository
) : ViewModel() {

    val topStoriesFlow = repository.topStoriesFlow()
        .cachedIn(viewModelScope)

    class Factory(
        private val repository: HackerNewsRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}