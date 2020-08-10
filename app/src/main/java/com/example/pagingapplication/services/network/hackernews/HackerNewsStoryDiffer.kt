package com.example.pagingapplication.services.network.hackernews

import androidx.recyclerview.widget.DiffUtil
import com.example.pagingapplication.services.network.hackernews.HackerNews

object HackerNewsStoryDiffer : DiffUtil.ItemCallback<HackerNews.Story>() {
    override fun areItemsTheSame(oldItem: HackerNews.Story, newItem: HackerNews.Story): Boolean = oldItem.id == oldItem.id
    override fun areContentsTheSame(oldItem: HackerNews.Story, newItem: HackerNews.Story): Boolean = oldItem == newItem
}