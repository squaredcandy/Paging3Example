package com.example.pagingapplication.services.database.hackernews

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.pagingapplication.services.database.Database
import com.example.pagingapplication.services.database.hackernews.model.HackerNewsRemoteKey
import com.example.pagingapplication.services.network.hackernews.HackerNewsRepository
import com.example.pagingapplication.services.network.hackernews.model.HackerNewsItemResult
import retrofit2.HttpException
import java.io.IOException

/**
 * This is the [RemoteMediator] which meditates our api and local database cache
 */
@ExperimentalPagingApi
class HackerNewsTopStoriesRemoteMediator(
    private val repository: HackerNewsRepository,
    private val database: Database
) : RemoteMediator<Int, HackerNewsItemResult>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HackerNewsItemResult>
    ): MediatorResult = tryLoad {
        /**
         * We handle the keys ourselves since the one provided in [state] is pretty poor
         */
        val remoteKey = repository.getRemoteKey(TOP_STORIES_INDEX) ?: createNewRemoteKey()

        val page: Int = when(loadType) {
            /**
             * If we are refreshing we are most likely trying to load the first page
             */
            LoadType.REFRESH -> 0
            /**
             * Disable prepending, [RemoteMediator] will keep loading forever if don't disable it
             * since we are always providing a [HackerNewsRemoteKey.prevKey] to load. If we wanted
             * to do it properly we will need to only give it a [HackerNewsRemoteKey.prevKey] if
             * there are updates in the previously loaded items but that is outside the scope of
             * this sample
             */
            LoadType.PREPEND -> return@tryLoad true
            LoadType.APPEND -> remoteKey.nextKey ?: return@tryLoad true
        }

        /**
         * Get the data, first try from cache otherwise use the network
         */
        val loadSize = state.config.pageSize
        val startIndex = page * loadSize
        val endIndex = (page + 1) * loadSize

        val itemIdsToLoad = remoteKey.itemIds.subList(startIndex, endIndex)
        val items = itemIdsToLoad.map { repository.getItemWithCache(it) }
        val newRemoteKey = remoteKey.copy(
            prevKey = if((page - 1) * loadSize >= 0) page - 1 else null,
            nextKey = if((page + 2) * loadSize < remoteKey.itemIds.lastIndex) page + 1 else null
        )

        database.withTransaction {
            if(loadType == LoadType.REFRESH) {
                createNewRemoteKey()
            }

            /**
             * Update the remote key and add items to cache
             * The [HackerNewsDao] will pick up the changes and invalidate the [PagingSource]
             * which will then tell our [Pager] to reload with new changes
             */
            repository.insertRemoteKey(newRemoteKey)
            repository.insertItemsToCache(items)
        }
        items.isNotEmpty()
    }

    private suspend fun createNewRemoteKey(): HackerNewsRemoteKey {
        return database.withTransaction {
            repository.deleteAllCachedItems()
            val itemIds = repository.getTopStoryIndices()

            val key = HackerNewsRemoteKey(
                TOP_STORIES_INDEX,
                itemIds,
                0,
                0
            )
            repository.insertRemoteKey(key)
            key
        }
    }

    private suspend fun tryLoad(
        block: suspend () -> Boolean
    ): MediatorResult {
        return try {
            MediatorResult.Success(block())
        } catch (e: IOException) {
            Log.e("Error", Log.getStackTraceString(e))
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("Error", Log.getStackTraceString(e))
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val TOP_STORIES_INDEX = "top_stories"
    }
}