package com.coroutine.coroutineplayground.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val mutableSearchStateLiveData: MutableLiveData<SearchScreen> =
        MutableLiveData(SearchScreen.Loading)
    val searchStateLiveData: LiveData<SearchScreen> = mutableSearchStateLiveData

    private var job: Job? = null

    private val mutableStateFlow = MutableStateFlow(false)
    private val stateFlow = mutableStateFlow.flatMapLatest {
        getSearchScreenFlow()
    }.onStart {
        emit(SearchScreen.Loading)
    }.catch {
        emit(SearchScreen.Error)
    }

    init {
        fetchListings()
    }


    fun fetchListings() {
        job?.cancel()

        job = viewModelScope.launch {
            getSearchScreenFlow().onStart {
                emit(SearchScreen.Loading)
            }.catch {
                emit(SearchScreen.Error)
            }.collect {
                mutableSearchStateLiveData.value = it
            }
        }
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