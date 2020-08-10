package com.example.pagingapplication.services.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pagingapplication.services.database.hackernews.HackerNewsDao
import com.example.pagingapplication.services.database.hackernews.model.HackerNewsRemoteKey
import com.example.pagingapplication.services.database.hackernews.HackerNewsRemoteKeysDao
import com.example.pagingapplication.services.network.hackernews.model.HackerNewsItemResult

@Database(entities = [HackerNewsItemResult::class, HackerNewsRemoteKey::class], version = 1, exportSchema = false)
@TypeConverters(ItemTypeConverter::class, IntListConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
    abstract fun hackerNewsRemoteKeysDao(): HackerNewsRemoteKeysDao
}