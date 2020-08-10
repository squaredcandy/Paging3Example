package com.example.pagingapplication.services.database.hackernews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.paging.RemoteMediator

/**
 * Since the [RemoteMediator] doesn't handle the paging well, we handle it ourselves
 */
@Entity(tableName = "remote_keys")
data class HackerNewsRemoteKey(
    // Id so we can differentiate between different remote keys
    @PrimaryKey val id: String,
    // HackerNews gives us all the itemIds at once so we store it here
    val itemIds: List<Int>,
    // Hold the previous and next keys
    val prevKey: Int?,
    val nextKey: Int?
)