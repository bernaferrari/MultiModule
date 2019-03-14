package com.biblialibras.android.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.biblialibras.android.bible.BuildersModule
import com.biblialibras.android.bible.main.FragmentBible
import com.biblialibras.android.repo.DatabaseModule
import com.biblialibras.android.repo.VideosDao
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.*
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider

@AssistedModule
@Module(includes = [AssistedInject_AppAssistedModule::class])
abstract class AppAssistedModule

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context = application.applicationContext

    @Provides
    fun sharedPrefs(application: MainApplication): SharedPreferences {
        return application.getSharedPreferences("workerPreferences", Context.MODE_PRIVATE)
    }
}

@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModelFactory(factory: TiviViewModelFactory): ViewModelProvider.Factory
}

class TiviViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

@Module
class ShowDetailsModule {

    @Provides
    fun provideActivity(activity: NavMainActivity): Context = activity

}

@Module
abstract class HomeBuilder {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class,
            ShowDetailsModule::class,
            BuildersModule::class
        ]
    )
    internal abstract fun bindDetailsActivity(): NavMainActivity
}

// If I use:
// HomeBuilder::class -> crash on execution (No injector factory bound for Class)
// BuildersModule::class -> crash on compile ([Dagger/MissingBinding] com.biblialibras.android.bible.main.ViewModelBible.Factory cannot be provided without an @Provides-annotated method)

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppAssistedModule::class,
        AppModule::class,
        DatabaseModule::class,
        HomeBuilder::class // or BuildersModule
    ]
)
@Singleton
interface SingletonComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MainApplication): Builder

        fun build(): SingletonComponent
    }

    fun inject(app: MainApplication)

    //    fun appContext(): Context
    fun sharedPrefs(): SharedPreferences

    fun videosDao(): VideosDao
}
