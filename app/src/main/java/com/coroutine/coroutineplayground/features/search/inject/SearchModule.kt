package com.coroutine.coroutineplayground.features.search.inject

import com.coroutine.coroutineplayground.features.common.SearchApi
import com.coroutine.coroutineplayground.features.search.remote.SearchRemoteDataSource
import com.coroutine.coroutineplayground.features.search.remote.SearchRepository
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
    @Provides
    fun provideSearchRemoteDataSource(
        searchApi: SearchApi
    ): SearchRemoteDataSource =
        SearchRemoteDataSource(searchApi)

    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchRemoteDataSource,
        @CoroutineDispatcherIO dispatcher: CoroutineDispatcher
    ): SearchRepository =
        SearchRepository(searchRemoteDataSource, dispatcher)

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