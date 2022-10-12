package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val mutableSearchStateLiveData: MutableLiveData<SearchState> =
        MutableLiveData(SearchState.Loading)
    val searchStateLiveData: LiveData<SearchState> = mutableSearchStateLiveData

    private var job: Job? = null

    init {
        fetchListings()
    }

    fun fetchListings() {
        job?.cancel()

        job = viewModelScope.launch {
            getStateFlow().onStart {
                emit(SearchState.Loading)
            }.catch {
                emit(SearchState.Error)
            }.collect {
                mutableSearchStateLiveData.value = it
            }
        }
    }

    private fun getStateFlow(): Flow<SearchState> {
        return searchRepository.getListings().map {
            SearchState.Success(it)
        }
    }
}

sealed class SearchState {
    data class Success(val searchModel: SearchModel) : SearchState()
    object Loading : SearchState()
    object Error : SearchState()
}