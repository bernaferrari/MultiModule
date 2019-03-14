package com.biblialibras.android.bible

import com.biblialibras.android.bible.main.FragmentBible
import com.biblialibras.android.bible.search.FragmentSearch
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bibleFragment(): FragmentBible

    @ContributesAndroidInjector
    abstract fun searchFragment(): FragmentSearch
}
