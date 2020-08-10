package com.example.pagingapplication.main

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingapplication.R
import com.example.pagingapplication.utils.inflate

/**
 * This footer adapter will allow us to show the user that new data is being loaded or an error
 * so the user can retry loading again.
 */
class HackerNewsFooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HackerNewsFooterAdapter.ViewHolder>() {
    sealed class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        class Loading(view: View) : ViewHolder(view)
        class Error(view: View, retry: () -> Unit) : ViewHolder(view) {
            private val button: Button = view.findViewById(R.id.hacker_news_placeholder_button)

            init {
                button.setOnClickListener {
                    retry()
                }
            }

            fun bind(loadState: LoadState) {
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        when(holder) {
            is ViewHolder.Loading -> {}
            is ViewHolder.Error -> {
                holder.bind(loadState)
            }
        }
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return when(loadState) {
            is LoadState.NotLoading -> R.layout.item_hacker_news_placeholder_loading
            LoadState.Loading -> R.layout.item_hacker_news_placeholder_loading
            is LoadState.Error -> R.layout.item_hacker_news_placeholder_error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = parent.inflate(getStateViewType(loadState), false)
        return when(loadState) {
            is LoadState.NotLoading -> throw IllegalStateException()
            LoadState.Loading -> ViewHolder.Loading(view)
            is LoadState.Error -> ViewHolder.Error(view, retry)
        }
    }
}