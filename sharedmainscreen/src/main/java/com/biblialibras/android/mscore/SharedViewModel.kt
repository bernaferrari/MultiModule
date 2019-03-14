package com.biblialibras.android.mscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.biblialibras.android.repo.VideoItem
import com.jakewharton.rxrelay2.PublishRelay
import java.util.*
import java.util.concurrent.TimeUnit

class SharedViewModel : ViewModel() {

    val videoHasLoaded = PublishRelay.create<Unit>()

    val currentVideoInfo = MutableLiveData<CurrentVideoInfo>()

    var pausedInOverlay = false

    val currentKind = MutableLiveData<String>()
}