package com.example.pagingapplication.services.network.hackernews

data class HackerNewsItemResult(
    val id: Int = -1,
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
    val deleted: Boolean = false
)