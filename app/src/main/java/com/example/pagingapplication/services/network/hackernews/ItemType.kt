package com.example.pagingapplication.services.network.hackernews

enum class ItemType(val jsonKey: String) {
    Job("job"),
    Story("story"),
    Comment("comment"),
    Poll("poll"),
    PollOpt("pollopt"),
}

