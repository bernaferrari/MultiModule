package com.biblialibras.android.bible.main

import com.airbnb.mvrx.*
import com.biblialibras.android.common.mvrx.MvRxViewModel
import com.biblialibras.android.bible.main.FragmentBible.Companion.MAX_NUM_OF_ELEMENTS
import com.biblialibras.android.repo.VideoWithCount
import com.biblialibras.android.repo.VideosDao
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

data class BibleState(val listOfItems: Async<List<VideoWithCount>> = Loading()) : MvRxState

class ViewModelBible @AssistedInject constructor(
    @Assisted initialState: BibleState,
    private val mVideosDao: VideosDao
) : MvRxViewModel<BibleState>(initialState) {

    init {
        fetchData()
    }

    private fun fetchData() = withState { _ ->
        mVideosDao.getVideosForBible(MAX_NUM_OF_ELEMENTS)
            .toObservable()
            .execute { copy(listOfItems = it) }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: BibleState): ViewModelBible
    }

    companion object : MvRxViewModelFactory<ViewModelBible, BibleState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: BibleState
        ): ViewModelBible? {
            val fragment: FragmentBible = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.bibleViewModelFactory.create(state)
        }
    }
}
