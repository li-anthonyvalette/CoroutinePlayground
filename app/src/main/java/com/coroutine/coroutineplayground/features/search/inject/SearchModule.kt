package com.coroutine.coroutineplayground.features.search.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.text.NumberFormat
import java.util.*
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class SearchModule {
    @CoroutineDispatcherIO
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @CoroutineDispatcherDefault
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun providePriceFormat(): NumberFormat {
        val currentLocale: Locale = Locale.getDefault()
        return NumberFormat.getInstance(currentLocale)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherDefault