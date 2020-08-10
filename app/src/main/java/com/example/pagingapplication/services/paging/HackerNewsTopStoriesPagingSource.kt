package com.example.pagingapplication.services.paging

import androidx.paging.PagingSource
import com.example.pagingapplication.model.HackerNews
import com.example.pagingapplication.model.toHackerNewsItem
import com.example.pagingapplication.services.network.hackernews.api.HackerNewsApi

class HackerNewsTopStoriesPagingSource(
    private val api: HackerNewsApi
) : PagingSource<Int, HackerNews.Story>() {
    /**
     * Cache the indices so we can reuse the same list every time
     */
    private var indicesCache: List<Int> = emptyList()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HackerNews.Story> = tryLoad {
        val page = params.key ?: 0
        val loadSize = params.loadSize
        val indices = if(indicesCache.isEmpty()) {
            api.getTopStories().also { indicesCache = it }
        } else {
            indicesCache
        }
        val startIndex = page * loadSize
        val endIndex = (page + 1) * loadSize

        val indicesToLoad = indices.subList(startIndex, endIndex)
        val items = indicesToLoad.map { api.getItem(it) }
        val data = items.map { it.toHackerNewsItem() as HackerNews.Story }

        /**
         * Create new prev/next keys if they are valid
         */
        val prevKey = if((page - 1) * loadSize >= 0) page - 1 else null
        val nextKey = if((page + 2) * loadSize < indices.lastIndex) page + 1 else null

        LoadResult.Page(
            data = data,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    private suspend fun <Key: Any, Value: Any> tryLoad(
        block: suspend () -> LoadResult.Page<Key, Value>
    ): LoadResult<Key, Value> {
        return try {
            block()
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}