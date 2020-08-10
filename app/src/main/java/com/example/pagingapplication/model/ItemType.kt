package com.example.pagingapplication.model

enum class ItemType(val jsonKey: String) {
    Job("job"),
    Story("story"),
    Comment("comment"),
    Poll("poll"),
    PollOpt("pollopt"),
}

