package com.coroutine.coroutineplayground.features.search.repository

import com.coroutine.coroutineplayground.features.search.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getListings(): Flow<SearchModel>
}