package com.example.pagingapplication.services

import com.example.pagingapplication.services.hackernews.HackerNewsRepository

data class ServiceContainer(
    val repository: HackerNewsRepository
)