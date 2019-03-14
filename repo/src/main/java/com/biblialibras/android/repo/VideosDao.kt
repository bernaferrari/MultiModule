package com.biblialibras.android.repo

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable

/**
 * Data Access Object for the tasks table.
 */
@Dao
interface VideosDao {

    @get:Query("SELECT * FROM videos")
    val all: List<VideoItem>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM videos WHERE kind =:kind")
    fun getByKind(kind: String): List<VideoItem>

    @Query("SELECT * FROM videos WHERE kind =:kind")
    fun getByKindPaged(kind: String): DataSource.Factory<Int, VideoItem>

    @Query("SELECT * FROM videos WHERE kind =:kind ORDER BY RANDOM()")
    fun getByKindPagedRandom(kind: String): DataSource.Factory<Int, VideoItem>

    @Query("SELECT * FROM videos WHERE kind =\"bible\" ORDER BY currentBook, rangeStart")
    fun getBibleFlowable(): Flowable<List<VideoItem>>

    @Query("SELECT * FROM videos WHERE kind =:kind ORDER BY RANDOM() LIMIT :limit")
    fun getByKindWithLimitRandom(kind: String, limit: Int): Flowable<List<VideoItem>>

    @Query("SELECT * FROM videos WHERE kind =:kind ORDER BY timestamp LIMIT :limit")
    fun getByKindWithLimit(kind: String, limit: Int): Flowable<List<VideoItem>>

    @Query("SELECT count(*) FROM videos WHERE kind =:kind")
    fun getCountForKind(kind: String): Int

    @Query("SELECT id FROM videos WHERE kind =:kind")
    fun getIdsListForKind(kind: String): List<String>

    @Query("SELECT * FROM videos t1 INNER JOIN (SELECT currentBook, count(*) as totalCount FROM videos t2 WHERE t2.kind=\"bible\" GROUP BY t2.currentBook) as t2 ON t1.currentBook = t2.currentBook WHERE  t1.kind=\"bible\" AND t1.verseIndex < :limit ORDER BY t1.rangeStart")
    fun getVideosForBible(limit: Int): Flowable<List<VideoWithCount>>

    @RawQuery(observedEntities = [VideoItem::class])
    fun getSearchQuery(query: SupportSQLiteQuery): Flowable<List<VideoItem>>

    /**
     * Select a task by id.
     *
     * @param id the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM videos WHERE id = :id")
    fun getTaskById(id: String): VideoItem

    /**
     * Insert a videoItem in the database. If the videoItem already exists, replace it.
     *
     * @param videoItem the videoItem to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(videoItem: VideoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfVideos(videosList: List<VideoItem>)


    /**
     * Update a videoItem.
     *
     * @param videoItem videoItem to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    fun updateTask(videoItem: VideoItem): Int

    /**
     * Update the complete status of a task
     *
     * @param id      id of the task
     * @param watched status to be updated
     */
    @Query("UPDATE videos SET isWatched = :watched WHERE id = :id")
    fun updateCompleted(id: String, watched: Boolean)

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM videos WHERE id = :id")
    fun deleteTaskById(id: String): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM videos")
    fun deleteTasks()
}
