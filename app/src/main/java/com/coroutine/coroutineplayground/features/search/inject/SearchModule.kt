package com.coroutine.coroutineplayground.features.search.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
class SearchModule {
    @CoroutineDispatcherIO
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @CoroutineDispatcherDefault
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherDefault