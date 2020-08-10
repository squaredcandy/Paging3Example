package com.example.pagingapplication.services.hackernews

import android.content.Context
import com.example.pagingapplication.services.database.DatabaseFactory
import com.example.pagingapplication.services.network.NetworkFactory

object HackerNewsFactory {

    fun createHackerNewsRepository(baseUrl: String): HackerNewsRepository {
        val api = NetworkFactory.createHackerNewsApi(baseUrl)
        return RealHackerNewsRepository(api)
    }

    fun createCachedHackerNewsRepository(
        applicationContext: Context,
        baseUrl: String
    ): HackerNewsRepository {
        val api = NetworkFactory.createHackerNewsApi(baseUrl)
        val database = DatabaseFactory.getDatabase(applicationContext)
        return RealCachedHackerNewsRepository(api, database)
    }
}