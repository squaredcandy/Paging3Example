package com.example.pagingapplication.services.database.hackernews.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagingapplication.services.database.hackernews.model.HackerNewsRemoteKey

@Dao
interface HackerNewsRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(remoteKey: HackerNewsRemoteKey)

    @Query("SELECT * FROM remote_keys WHERE id = :key")
    suspend fun getHackerNewsRemoteKey(key: String): HackerNewsRemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()
}