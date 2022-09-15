package com.coroutine.coroutineplayground.features.search.remote

import com.coroutine.coroutineplayground.features.search.model.SearchModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) {
    fun getListings(): Flow<SearchModel> {
        return flow {
            val listings = searchRemoteDataSource.getListings()
            emit(SearchModel(listings))
        }.flowOn(dispatcher)
    }
}