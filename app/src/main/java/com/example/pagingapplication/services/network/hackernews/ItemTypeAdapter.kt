package com.example.pagingapplication.services.network.hackernews

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

object ItemTypeAdapter : JsonAdapter<ItemType>() {
    override fun fromJson(reader: JsonReader): ItemType? {
        val str = reader.nextString()
        return ItemType.valueOf(str)
    }
    override fun toJson(writer: JsonWriter, value: ItemType?) {
        writer.value(value?.jsonKey)
    }
}