package com.example.pagingapplication.services.database

import android.content.Context
import androidx.room.Room
import com.example.pagingapplication.services.database.hackernews.dao.HackerNewsDao
import com.example.pagingapplication.services.database.hackernews.dao.HackerNewsRemoteKeysDao

object DatabaseFactory {

    private const val DATABASE_NAME = "database.db"
    @Volatile
    private var instance: HackerNewsDatabase? = null

    fun getDatabase(applicationContext: Context): HackerNewsDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(applicationContext).also { instance = it }
        }
    }

    private fun buildDatabase(applicationContext: Context): HackerNewsDatabase =
        Room.databaseBuilder(applicationContext, HackerNewsDatabase::class.java, DATABASE_NAME)
            .build()

    fun getHackerNewsDao(applicationContext: Context): HackerNewsDao {
        return getDatabase(applicationContext).hackerNewsDao()
    }

    fun getHackerNewsRemoteKeysDao(applicationContext: Context): HackerNewsRemoteKeysDao {
        return getDatabase(applicationContext).hackerNewsRemoteKeysDao()
    }
}