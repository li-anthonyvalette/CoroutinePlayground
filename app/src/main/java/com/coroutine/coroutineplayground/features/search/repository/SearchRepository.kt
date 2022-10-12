package com.coroutine.coroutineplayground.features.search.repository

import com.coroutine.coroutineplayground.features.common.model.Listing
import com.coroutine.coroutineplayground.features.search.inject.CoroutineDispatcherIO
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.remote.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    @CoroutineDispatcherIO private val dispatcher: CoroutineDispatcher,
    private val searchModelTransformer: SearchModelTransformer
) {
    fun getListings(): Flow<SearchModel> {
        return getListingsFlow().map {
            searchModelTransformer.apply(it)
        }.flowOn(dispatcher)
    }

    private fun getListingsFlow(): Flow<List<Listing>> {
        return flow {
            emit(searchRemoteDataSource.getListings())
        }
    }
}