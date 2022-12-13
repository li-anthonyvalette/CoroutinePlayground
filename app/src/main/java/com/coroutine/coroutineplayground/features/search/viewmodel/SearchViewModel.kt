package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchRepository: SearchRepository
) : ViewModel() {

    /**
     * Tip for Android apps! You can use WhileSubscribed(5000) most of the time to keep the upstream
     * flow active for 5 seconds more after the disappearance of the last collector.
     * That avoids restarting the upstream flow in certain situations such as configuration changes.
     * This tip is especially helpful when upstream flows are expensive to create and
     * when these operators are used in ViewModels.
     * Source: https://manuelvivo.dev/sharein-statein
     */
    val searchScreenState: StateFlow<SearchScreenState> = searchRepository.getListings().map {
        SearchScreenState.Success(it)
    }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchScreenState.Loading
        )

    private val mutableSharedFlow =
        MutableSharedFlow<Boolean>(1)

    fun fetchListings() {
        mutableSharedFlow.tryEmit(true)
    }
}

sealed class SearchScreenState {
    data class Success(val searchModel: SearchModel) : SearchScreenState()
    object Loading : SearchScreenState()
    object Error : SearchScreenState()
}