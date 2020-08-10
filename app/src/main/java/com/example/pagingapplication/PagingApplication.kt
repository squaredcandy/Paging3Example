package com.example.pagingapplication

import android.app.Application
import com.example.pagingapplication.services.ServiceContainer
import com.example.pagingapplication.services.network.NetworkFactory

class PagingApplication : Application() {

    private val baseUrl = "https://hacker-news.firebaseio.com/"
    val serviceContainer: ServiceContainer by lazy {
        ServiceContainer(
            NetworkFactory.createHackerNewsRepository(baseUrl)
        )
    }
}