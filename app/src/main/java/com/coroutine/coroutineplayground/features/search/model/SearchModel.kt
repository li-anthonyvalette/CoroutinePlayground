package com.coroutine.coroutineplayground.features.search.model

data class SearchModel(
    val items: List<ListingItem>
)

data class ListingItem(
    val city: String,
    val area: Float,
    val price: Float,
    val rooms: Int,
    val url: String
)