package com.example.pagingapplication.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pagingapplication.PagingApplication
import com.example.pagingapplication.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val adapter = HackerNewsAdapter()
    private val footerAdapter = HackerNewsFooterAdapter {
        adapter.retry()
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(
            (application as PagingApplication).serviceContainer.repository
        ) 
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentView = findViewById<View>(android.R.id.content)
        val toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar)
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.main_swipe_refresh)
        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)

        setSupportActionBar(toolbar)

        refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter.withLoadStateFooter(footerAdapter)

        /**
         * This flow will allow us to get back current state of the paging source so we
         * can update accordingly.
         */
        adapter.loadStateFlow.map {
            when(val currentState = it.refresh) {
                is LoadState.NotLoading -> {
                    refreshLayout.isRefreshing = false
                }
                LoadState.Loading -> {
                    refreshLayout.isRefreshing = true
                }
                is LoadState.Error -> {
                    refreshLayout.isRefreshing = false
                    val throwable = currentState.error
                    Snackbar.make(
                        contentView,
                        throwable.message ?: getString(R.string.paging_source_generic_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.topStoriesFlow.map { data ->
            adapter.submitData(data)
        }.launchIn(lifecycleScope)
    }
}