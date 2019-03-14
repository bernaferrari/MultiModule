package com.biblialibras.android.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable globalModel class for a VideoItem.
 */
@Entity(
    tableName = "videos"
)
open class VideoItem(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: String,
    val title: String,
    val description: String,
    val timestamp: Long,
    val duration: Long,
    val kind: String,
    val isWatched: Boolean,
    val currentBook: Int,
    val verseIndex: Int,
    val rangeStart: Int,
    val rangeEnd: Int
) {
    override fun toString(): String {
        return "VideoItem with title $title"
    }
}
