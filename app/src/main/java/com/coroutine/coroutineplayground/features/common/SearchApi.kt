package com.coroutine.coroutineplayground.features.common

import com.coroutine.coroutineplayground.features.common.model.SearchResponse
import retrofit2.http.GET

interface SearchApi {
    @GET("listings.json")
    suspend fun getListings(): SearchResponse
}
