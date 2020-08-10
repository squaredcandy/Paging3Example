package com.example.pagingapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.pagingapplication.model.HackerNews

object HackerNewsStoryDiffer : DiffUtil.ItemCallback<HackerNews.Story>() {
    override fun areItemsTheSame(oldItem: HackerNews.Story, newItem: HackerNews.Story): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HackerNews.Story, newItem: HackerNews.Story): Boolean {
        if (oldItem.id != newItem.id) return false
        if (oldItem.by != newItem.by) return false
        if (oldItem.descendants != newItem.descendants) return false
        if (oldItem.type != newItem.type) return false
        if (oldItem.score != newItem.score) return false
        if (oldItem.text != newItem.text) return false
        if (oldItem.time != newItem.time) return false
        if (oldItem.title != newItem.title) return false
        if (oldItem.url != newItem.url) return false
        if (oldItem.dead != newItem.dead) return false
        if (oldItem.deleted != newItem.deleted) return false
        return true
    }
}