package com.coroutine.coroutineplayground.inject

import com.coroutine.coroutineplayground.features.common.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSearchApi(): SearchApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SearchApi::class.java)
    }
}

private const val BASE_URL = "https://gsl-apps-technical-test.dignp.com/"