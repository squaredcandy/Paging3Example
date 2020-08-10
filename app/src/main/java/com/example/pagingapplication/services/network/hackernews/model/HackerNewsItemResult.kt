package com.example.pagingapplication.services.network.hackernews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pagingapplication.model.ItemType

@Entity(tableName = "hacker_news_items")
data class HackerNewsItemResult(
    @PrimaryKey val id: Int = -1,
    val itemType: ItemType = ItemType.Story,
    val by: String? = null,
    val descendants: Int? = null,
    val kids: List<Int> = emptyList(),
    val score: Int? = null,
    val text: String? = null,
    val time: Long? = null,
    val title: String? = null,
    val url: String? = null,
    val parent: Int? = null,
    val parts: List<Int> = emptyList(),
    val poll: Int? = null,
    val dead: Boolean = false,
    val deleted: Boolean = false,
    // Keep our items in order when retrieving them from the database
    val dateCreated: Long = System.currentTimeMillis()
)