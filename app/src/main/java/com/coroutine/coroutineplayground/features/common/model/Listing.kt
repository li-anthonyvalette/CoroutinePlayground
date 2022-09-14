package com.coroutine.coroutineplayground.features.common.model

data class Listing(
    val id: Int,
    val bedrooms: Int? = null,
    val city: String,
    val area: Float,
    val url: String? = null,
    val price: Float,
    val professional: String,
    val propertyType: String,
    val rooms: Int? = null
)
