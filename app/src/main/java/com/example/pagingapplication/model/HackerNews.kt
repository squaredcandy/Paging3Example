package com.example.pagingapplication.model

import com.example.pagingapplication.services.network.hackernews.model.HackerNewsItemResult

sealed class HackerNews(open val id: Int, val type: ItemType) {
    // Can also be an Ask
    data class Story(
        val by: String,
        val descendants: Int,
        override val id: Int,
        val kids: List<Int>,
        val score: Int,
        val text: String? = null,
        val time: Long,
        val title: String,
        val url: String? = null,
        val dead: Boolean = false,
        val deleted: Boolean = false
    ): HackerNews(id,
        ItemType.Story
    )

    data class Comment(
        val by: String,
        override val id: Int,
        val kids: List<Int>,
        val parent: Int,
        val text: String,
        val time: Long,
        val dead: Boolean = false,
        val deleted: Boolean = false
    ): HackerNews(id,
        ItemType.Comment
    )

    data class Job(
        val by: String,
        override val id: Int,
        val score: Int,
        val text: String,
        val time: Long,
        val title: String,
        val url: String? = null,
        val dead: Boolean = false,
        val deleted: Boolean = false
    ): HackerNews(id,
        ItemType.Job
    )

    data class Poll(
        val by: String,
        val descendants: Int,
        override val id: Int,
        val kids: List<Int>,
        val parts: List<Int>,
        val score: Int,
        val text: String,
        val time: Long,
        val title: String,
        val dead: Boolean = false,
        val deleted: Boolean = false
    ): HackerNews(id,
        ItemType.Poll
    )

    data class PollOpt(
        val by: String,
        override val id: Int,
        val poll: Int,
        val score: Int,
        val text: String,
        val time: Long,
        val dead: Boolean = false,
        val deleted: Boolean = false
    ): HackerNews(id,
        ItemType.PollOpt
    )
}

fun HackerNewsItemResult.toHackerNewsItem(): HackerNews {
    return when(itemType) {
        ItemType.Job -> {
            HackerNews.Job(
                by ?: "Anon",
                id,
                score ?: 0,
                text ?: "",
                time ?: 0L,
                title ?: "",
                url,
                dead,
                deleted
            )
        }
        ItemType.Story -> {
            HackerNews.Story(
                by ?: "Anon",
                descendants ?: kids.size,
                id,
                kids,
                score ?: 0,
                text,
                time ?: 0L,
                title ?: "",
                url,
                dead,
                deleted
            )
        }
        ItemType.Comment -> {
            HackerNews.Comment(
                by ?: "Anon",
                id,
                kids,
                parent!!,
                text ?: "",
                time ?: 0L,
                dead,
                deleted
            )
        }
        ItemType.Poll -> {
            HackerNews.Poll(
                by ?: "Anon",
                descendants ?: kids.size,
                id,
                kids,
                parts,
                score ?: 0,
                text ?: "",
                time ?: 0L,
                title ?: "",
                dead,
                deleted
            )
        }
        ItemType.PollOpt -> {
            HackerNews.PollOpt(
                by ?: "Anon",
                id,
                poll!!,
                score ?: 0,
                text ?: "",
                time ?: 0L,
                dead,
                deleted
            )
        }
    }
}