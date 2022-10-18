package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val mutableSearchStateLiveData: MutableLiveData<SearchScreen> =
        MutableLiveData(SearchScreen.Loading)
    val searchStateLiveData: LiveData<SearchScreen> = mutableSearchStateLiveData

    private val mutableShareFlow =
        MutableSharedFlow<Boolean>(1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply {
            tryEmit(true)
        }

    @OptIn(FlowPreview::class)
    private val shareFlow = mutableShareFlow.flatMapMerge {
        getSearchScreenFlow()
            .onStart {
                emit(SearchScreen.Loading)
            }
            .catch {
                emit(SearchScreen.Error)
            }
    }

    init {
        viewModelScope.launch {
            shareFlow.collect {
                mutableSearchStateLiveData.value = it
            }
        }
    }

    fun fetchListings() {
        mutableShareFlow.tryEmit(true)
    }

    private fun getSearchScreenFlow(): Flow<SearchScreen> {
        return searchRepository.getListings().map {
            SearchScreen.Success(it)
        }
    }
}

sealed class SearchScreen {
    data class Success(val searchModel: SearchModel) : SearchScreen()
    object Loading : SearchScreen()
    object Error : SearchScreen()
}