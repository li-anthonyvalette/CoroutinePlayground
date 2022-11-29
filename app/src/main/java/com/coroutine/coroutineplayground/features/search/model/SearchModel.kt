package com.coroutine.coroutineplayground.features.search.model

data class SearchModel(
    val items: List<ListingItem>
)

data class ListingItem(
    val city: String,
    val area: Float,
    val price: String,
    val rooms: Int,
    val url: String
)