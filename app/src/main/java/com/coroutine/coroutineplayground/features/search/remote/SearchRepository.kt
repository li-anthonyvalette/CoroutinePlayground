package com.coroutine.coroutineplayground.features.search.remote

import com.coroutine.coroutineplayground.features.search.model.SearchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) {
    fun getListings(): Flow<SearchModel> {
        return flow {
            val listings = searchRemoteDataSource.getListings()
            emit(SearchModel(listings))
        }.flowOn(Dispatchers.IO) // todo inject this dispatcher in constructor with Hilt
    }
}