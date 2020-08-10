package com.example.pagingapplication.services.database.hackernews

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagingapplication.services.network.hackernews.model.HackerNewsItemResult

@Dao
interface HackerNewsDao {
    @Query("SELECT * FROM hacker_news_items ORDER BY dateCreated ASC")
    fun getPagingSource(): PagingSource<Int, HackerNewsItemResult>

    @Query("SELECT * FROM hacker_news_items WHERE id = :itemId")
    suspend fun getItem(itemId: Int): HackerNewsItemResult?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<HackerNewsItemResult>)

    @Query("DELETE from hacker_news_items")
    suspend fun deleteAllItems()
}