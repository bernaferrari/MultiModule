package com.biblialibras.android.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class VideoWithCount(
    id: String,
    title: String,
    description: String,
    timestamp: Long,
    duration: Long,
    kind: String,
    isWatched: Boolean,
    currentBook: Int,
    verseIndex: Int,
    rangeStart: Int,
    rangeEnd: Int,
    val totalCount: Int
) : VideoItem(
    id,
    title,
    description,
    timestamp,
    duration,
    kind,
    isWatched,
    currentBook,
    verseIndex,
    rangeStart,
    rangeEnd
)
