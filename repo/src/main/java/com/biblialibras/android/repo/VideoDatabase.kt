package com.biblialibras.android.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biblialibras.android.repo.VideoItem
import com.biblialibras.android.repo.VideosDao

/**
 * The Room Database that contains the VideoItem table.
 */
@Database(entities = [VideoItem::class], version = 4, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videosDao(): VideosDao

}
