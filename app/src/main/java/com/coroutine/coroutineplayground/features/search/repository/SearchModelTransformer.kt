package com.coroutine.coroutineplayground.features.search.repository

import androidx.annotation.WorkerThread
import com.coroutine.coroutineplayground.features.common.model.Listing
import com.coroutine.coroutineplayground.features.search.model.ListingItem
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import javax.inject.Inject


class SearchModelTransformer @Inject constructor() {

    @WorkerThread
    fun apply(listings: List<Listing>): SearchModel {
        return SearchModel(
            listings.map { listing ->
                ListingItem(
                    listing.city,
                    listing.area,
                    listing.price,
                    listing.rooms ?: 0,
                    listing.url ?: ""
                )
            }
        )
    }
}
