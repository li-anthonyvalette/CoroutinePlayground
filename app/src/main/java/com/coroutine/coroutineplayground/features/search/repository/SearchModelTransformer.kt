package com.coroutine.coroutineplayground.features.search.repository

import androidx.annotation.WorkerThread
import com.coroutine.coroutineplayground.features.common.model.Listing
import com.coroutine.coroutineplayground.features.search.model.ListingItem
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


class SearchModelTransformer @Inject constructor(
    private val priceFormat: NumberFormat
) {

    @WorkerThread
    fun apply(listings: List<Listing>): SearchModel {
        return SearchModel(
            listings.map { listing ->
                ListingItem(
                    listing.city,
                    listing.area,
                    priceFormat.format(listing.price) + " €",
                    listing.rooms ?: 0,
                    listing.url ?: ""
                )
            }
        )
    }
}