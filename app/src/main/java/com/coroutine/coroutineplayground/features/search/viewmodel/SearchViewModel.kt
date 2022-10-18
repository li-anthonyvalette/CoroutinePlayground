package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.coroutineplayground.features.search.inject.CoroutineDispatcherDefault
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    @CoroutineDispatcherDefault private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableSearchStateLiveData: MutableLiveData<SearchScreenState> =
        MutableLiveData(SearchScreenState.Loading)
    val searchStateLiveData: LiveData<SearchScreenState> = mutableSearchStateLiveData

    private val mutableShareFlow =
        MutableSharedFlow<Boolean>(1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply {
            tryEmit(true)
        }

    init {
        viewModelScope.launch {
            getShareFlow().collect {
                mutableSearchStateLiveData.value = it
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getShareFlow(): Flow<SearchScreenState> = mutableShareFlow.flatMapMerge {
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
        mutableShareFlow.tryEmit(true)
    }
}

sealed class SearchScreenState {
    data class Success(val searchModel: SearchModel) : SearchScreenState()
    object Loading : SearchScreenState()
    object Error : SearchScreenState()
}