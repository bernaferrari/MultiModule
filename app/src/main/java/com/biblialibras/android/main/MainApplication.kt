package com.biblialibras.android.main

import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.biblialibras.android.BuildConfig
import com.devbrackets.android.exomedia.ExoMedia
import com.facebook.stetho.Stetho
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import jonathanfinerty.once.Once
import okhttp3.OkHttpClient
import java.io.File
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class MainApplication : MultiDexApplication(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    lateinit var component: SingletonComponent

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        component = DaggerSingletonComponent.builder()
            .application(this)
            .build()
            .also {
                it.inject(this)
            }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        Once.initialise(this)
        configureExoMedia()
    }

    private fun configureExoMedia() {
        // Registers the media sources to use the OkHttp client instead of the standard Apache one
        // Note: the OkHttpDataSourceFactory can be found in the ExoPlayer extension library `extension-okhttp`

        ExoMedia.setDataSourceFactoryProvider(object : ExoMedia.DataSourceFactoryProvider {
            private var instance: CacheDataSourceFactory? = null

            override fun provide(
                userAgent: String,
                listener: TransferListener?
            ): DataSource.Factory {
                if (instance == null) {
                    // Updates the network data source to use the OKHttp implementation
                    val upstreamFactory =
                        OkHttpDataSourceFactory(OkHttpClient(), userAgent, listener)

                    // Adds a cache around the upstreamFactory
                    val cache = SimpleCache(
                        File(cacheDir, "ExoMediaCache"),
                        LeastRecentlyUsedCacheEvictor((50 * 1024 * 1024).toLong())
                    )
                    instance = CacheDataSourceFactory(
                        cache,
                        upstreamFactory,
                        CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
                    )
                }

                return instance!!
            }
        })
    }

    companion object {
        private var INSTANCE: MainApplication? = null

        @JvmStatic
        fun get(): MainApplication =
            INSTANCE ?: throw NullPointerException("MainApplication INSTANCE must not be null")
    }
}