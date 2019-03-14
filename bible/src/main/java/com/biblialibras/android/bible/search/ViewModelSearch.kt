package com.biblialibras.android.bible.search

import androidx.sqlite.db.SimpleSQLiteQuery
import com.airbnb.mvrx.*
import com.biblialibras.android.common.mvrx.MvRxViewModel
import com.biblialibras.android.repo.VideoItem
import com.biblialibras.android.repo.VideosDao

data class SearchState(
    val listOfItems: Async<List<VideoItem>> = Uninitialized,
    val isLoading: Boolean = false,
    val isSearchFieldEmpty: Boolean = true
) : MvRxState

class ViewModelSearch(
    initialState: SearchState,
    private val mVideosDao: VideosDao
) : MvRxViewModel<SearchState>(initialState) {

    fun parseSearch(search: CharSequence) {
        if (search.isEmpty()) {
            setState { copy(isSearchFieldEmpty = true) }
        } else {
            setState { copy(isSearchFieldEmpty = false, isLoading = true) }
            fetchData(getQuery(search.toString()))
        }
    }

    private fun getQuery(search: String): SimpleSQLiteQuery {
        var query = "SELECT * FROM videos WHERE kind = \"bible\" "
        query += "LIMIT 200"
        return SimpleSQLiteQuery(query)
    }

    fun fetchData(query: SimpleSQLiteQuery) = withState { _ ->
        mVideosDao.getSearchQuery(query = query).toObservable()
            .execute { copy(listOfItems = it, isLoading = false) }
    }
}
