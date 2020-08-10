package com.example.pagingapplication.services

import com.example.pagingapplication.services.network.hackernews.HackerNewsRepository

data class ServiceContainer(
    val repository: HackerNewsRepository
)