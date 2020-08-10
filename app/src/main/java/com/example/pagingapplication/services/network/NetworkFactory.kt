package com.example.pagingapplication.services.network

import com.example.pagingapplication.services.network.hackernews.HackerNewsApi
import com.example.pagingapplication.services.network.hackernews.HackerNewsRepository
import com.example.pagingapplication.services.network.hackernews.ItemType
import com.example.pagingapplication.services.network.hackernews.ItemTypeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkFactory {

    private val moshi = Moshi.Builder()
        .add(ItemType::class.java, ItemTypeAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun createHackerNewsApi(baseUrl: String): HackerNewsApi {
        val client = createOkHttpClient()
        val retrofit = createRetrofit(client, baseUrl)
        return retrofit.create(HackerNewsApi::class.java)
    }

    fun createHackerNewsRepository(baseUrl: String): HackerNewsRepository {
        val api = createHackerNewsApi(baseUrl)
        return HackerNewsRepository(api)
    }
}