package com.coroutine.coroutineplayground.features.search.model

import com.coroutine.coroutineplayground.features.common.model.Listing

data class SearchModel(
    val items: List<ListingItem>
)

data class ListingItem(
    val city: String,
    val area: Float,
    val price: Float,
    val rooms: Int
)