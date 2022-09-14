package com.coroutine.coroutineplayground.features.common

import com.coroutine.coroutineplayground.features.common.model.Listing
import com.coroutine.coroutineplayground.features.common.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApi {
    @GET("listings.json")
    suspend fun getListings(): SearchResponse

    @GET("listings/{listingId}.json")
    suspend fun getListingById(@Path("listingId") id: Int): Listing
}
