package com.example.pagingapplication.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingapplication.R
import com.example.pagingapplication.model.HackerNews
import com.example.pagingapplication.utils.HackerNewsStoryDiffer
import com.example.pagingapplication.utils.inflate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Our recycler adapter for displaying paging data. It inherits from [PagingDataAdapter] which
 * contains methods automatically poll more data from the [PagingSource] as well as methods to
 * monitor the state of the [PagingSource].
 */
class HackerNewsAdapter: PagingDataAdapter<HackerNews.Story, HackerNewsAdapter.ViewHolder>(
    HackerNewsStoryDiffer
) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val context: Context = view.context
        private val titleTextView: TextView = view.findViewById(R.id.hacker_news_item_title)
        private val authorTextView: TextView = view.findViewById(R.id.hacker_news_item_subtitle)
        private val scoreTextView: TextView = view.findViewById(R.id.hacker_news_item_score)
        private val commentsTextView: TextView = view.findViewById(R.id.hacker_news_item_comments)

        // Using Date so we don't need to pull in Java 8 libraries but you should use them
        private val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

        fun bind(item: HackerNews.Story) {
            val date = Date(item.time * 1000L)
            val dateNow = Date()
            val diffInMilli = dateNow.time - date.time
            val diffInHours = TimeUnit.HOURS.convert(diffInMilli, TimeUnit.MILLISECONDS).toInt()
            val dateFormatted = when {
                diffInHours < 1 -> {
                    // < 1 hr ago
                    context.getString(R.string.time_just_now)
                }
                diffInHours < 24 -> {
                    // hrs ago
                    context.resources.getQuantityString(R.plurals.time_hours_ago, diffInHours, diffInHours)
                }
                diffInHours < 48 -> {
                    // yesterday
                    context.getString(R.string.time_yesterday)
                }
                else -> {
                    // date
                    context.getString(R.string.time_on_date, dateFormat.format(date))
                }
            }

            titleTextView.text = item.title
            authorTextView.text = context.getString(R.string.hacker_news_item_subtitle, item.by, dateFormatted)
            scoreTextView.text = context.getString(R.string.hacker_news_item_score, item.score)
            commentsTextView.text = context.getString(R.string.hacker_news_item_comments, item.descendants)
        }

        fun bindPlaceholder() {
            titleTextView.text = ""
            authorTextView.text = ""
            scoreTextView.text = context.getString(R.string.hacker_news_item_score, 0)
            commentsTextView.text = context.getString(R.string.hacker_news_item_comments, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_hacker_news, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            holder.bind(item)
        } else {
            holder.bindPlaceholder()
        }
    }
}