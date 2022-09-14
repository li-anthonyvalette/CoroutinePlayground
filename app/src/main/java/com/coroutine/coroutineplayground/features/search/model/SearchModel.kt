package com.coroutine.coroutineplayground.features.search.model

import com.coroutine.coroutineplayground.features.common.model.Listing

data class SearchModel(
    val items: List<Listing> //todo change Listing to another model that is not coming directly from remote
)