package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.ViewModel
import com.coroutine.coroutineplayground.features.search.remote.SearchRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    fun fetchListings() {
        //todo call the repository and update a LiveData
    }
}