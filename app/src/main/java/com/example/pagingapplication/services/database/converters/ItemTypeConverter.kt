package com.example.pagingapplication.services.database

import androidx.room.TypeConverter
import com.example.pagingapplication.model.ItemType

class ItemTypeConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun fromString(value: String): ItemType {
            return ItemType.values().first { it.jsonKey == value }
        }

        @JvmStatic
        @TypeConverter
        fun fromItemType(value: ItemType): String {
            return value.jsonKey
        }
    }
}