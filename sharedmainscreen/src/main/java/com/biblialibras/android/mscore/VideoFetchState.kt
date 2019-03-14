package com.biblialibras.android.mscore

sealed class VideoFetchState {
    object Searching : VideoFetchState()
    object Loading : VideoFetchState()
    object Error : VideoFetchState()
    object Completed : VideoFetchState()
}
