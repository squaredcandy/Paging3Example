package com.example.pagingapplication

import android.app.Application
import com.example.pagingapplication.services.ServiceContainer
import com.example.pagingapplication.services.hackernews.HackerNewsFactory

class PagingApplication : Application() {

    private val baseUrl = "https://hacker-news.firebaseio.com/"
    lateinit var serviceContainer: ServiceContainer

    override fun onCreate() {
        super.onCreate()
        serviceContainer = ServiceContainer(
            HackerNewsFactory.createCachedHackerNewsRepository(this, baseUrl)
//            HackerNewsFactory.createHackerNewsRepository(baseUrl)
        )
    }
}