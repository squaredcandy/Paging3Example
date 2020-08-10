package com.example.pagingapplication.services.database

import androidx.room.TypeConverter
import com.example.pagingapplication.services.network.NetworkFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

class IntListConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun fromString(value: String): List<Int> {
            return getAdapter().fromJson(value) ?: emptyList()
        }

        @JvmStatic
        @TypeConverter
        fun fromItemType(value: List<Int>): String {
            return getAdapter().toJson(value)
        }

        private fun getAdapter(): JsonAdapter<List<Int>> {
            val parameterizedType = Types.newParameterizedType(
                List::class.java,
                Integer::class.java
            )
            return NetworkFactory.moshi.adapter(parameterizedType)
        }
    }
}