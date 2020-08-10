package com.example.pagingapplication.services.network.hackernews

import androidx.paging.PagingSource

class HackerNewsTopStoriesPagingSource(
    private val repository: HackerNewsRepository
) : PagingSource<Int, HackerNews.Story>() {
    /**
     * Cache the indices so we can reuse the same list every time
     */
    private var indicesCache: List<Int> = emptyList()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HackerNews.Story> {
        return try {
            val page = params.key ?: 0
            val loadSize = params.loadSize
            val indices = if(indicesCache.isEmpty()) {
                repository.getTopStoryIndices().also { indicesCache = it }
            } else {
                indicesCache
            }
            val startIndex = page * loadSize
            val endIndex = (page + 1) * loadSize

            val indicesToLoad = indices.subList(startIndex, endIndex)
            val items = indicesToLoad.map { repository.getItem(it) }
            val data = items.map { it.toHackerNewsItem() as HackerNews.Story }

            /**
             * Create new prev/next keys if they are valid
             */
            val prevKey = if((page - 1) * loadSize > 0) page - 1 else null
            val nextKey = if((page + 2) * loadSize < indices.lastIndex) page + 1 else null

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}