package com.biblialibras.android.repo

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    internal fun provideVideosDao(db: VideoDatabase): VideosDao = db.videosDao()

    @Singleton
    @Provides
    internal fun provideDb(context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            VideoDatabase::class.java,
            "BibliaLibras.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
