package com.coroutine.coroutineplayground.features.search.remote

import com.coroutine.coroutineplayground.features.common.SearchApi
import com.coroutine.coroutineplayground.features.common.model.Listing
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val searchApi: SearchApi
) {
    suspend fun getListings(): List<Listing> = searchApi.getListings().items
}
