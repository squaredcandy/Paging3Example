package com.example.pagingapplication.services.database

import android.content.Context
import androidx.room.Room
import com.example.pagingapplication.services.database.hackernews.HackerNewsDao
import com.example.pagingapplication.services.database.hackernews.HackerNewsRemoteKeysDao

object DatabaseFactory {

    private const val DATABASE_NAME = "database.db"
    @Volatile private var instance: Database? = null

    fun getDatabase(applicationContext: Context): Database {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(applicationContext) .also { instance = it }
        }
    }
    private fun buildDatabase(applicationContext: Context): Database =
        Room.databaseBuilder(applicationContext, Database::class.java, DATABASE_NAME).build()

    fun getHackerNewsDao(applicationContext: Context): HackerNewsDao {
        return getDatabase(applicationContext).hackerNewsDao()
    }

    fun getHackerNewsRemoteKeysDao(applicationContext: Context): HackerNewsRemoteKeysDao {
        return getDatabase(applicationContext).hackerNewsRemoteKeysDao()
    }
}