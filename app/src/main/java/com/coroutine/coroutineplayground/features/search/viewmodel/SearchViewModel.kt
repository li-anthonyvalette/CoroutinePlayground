package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.coroutineplayground.features.search.inject.CoroutineDispatcherDefault
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    @CoroutineDispatcherDefault private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableSearchStateFlow =
        MutableStateFlow<SearchScreenState>(SearchScreenState.Loading)
    val searchStateFlow: StateFlow<SearchScreenState>
        get() = mutableSearchStateFlow

    private val mutableSharedFlow =
        MutableSharedFlow<Boolean>(1)

    init {
        viewModelScope.launch {
            getShareFlow().collect {
                mutableSearchStateFlow.value = it
            }
        }

        mutableSharedFlow.tryEmit(true)
    }

    @OptIn(FlowPreview::class)
    private fun getShareFlow(): Flow<SearchScreenState> = mutableSharedFlow.flatMapMerge {
        getSearchScreenFlow()
            .onStart {
                emit(SearchScreenState.Loading)
            }
            .catch {
                emit(SearchScreenState.Error)
            }
    }

    private fun getSearchScreenFlow(): Flow<SearchScreenState> {
        return searchRepository.getListings().map {
            SearchScreenState.Success(it)
        }.flowOn(dispatcher)
    }

    fun fetchListings() {
        mutableSharedFlow.tryEmit(true)
    }
}

sealed class SearchScreenState {
    data class Success(val searchModel: SearchModel) : SearchScreenState()
    object Loading : SearchScreenState()
    object Error : SearchScreenState()
}