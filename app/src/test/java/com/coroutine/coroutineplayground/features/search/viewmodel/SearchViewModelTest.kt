package com.coroutine.coroutineplayground.features.search.viewmodel

import com.coroutine.coroutineplayground.features.search.model.ListingItem
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.repository.SearchRepository
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchScreenState.*
import com.coroutine.coroutineplayground.utils.MainDispatcherRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

/*  @Test
    fun `should load classified at initialisation`() = runTest {

        val sut = SearchViewModel(FakeSearchRepository, UnconfinedTestDispatcher())

        val values = mutableListOf<SearchScreenState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            sut.searchStateFlow.collect {
                values += it
            }
        }
        assertEquals(listOf(Success(A_SEARCH_MODEL)), values)
        collectJob.cancel()
    }*/

/*
    @Test
    fun `should load classified at initialisation`() = runTest {

        val sut = SearchViewModel(FakeSearchRepository, UnconfinedTestDispatcher())

        assertEquals(listOf(Success(A_SEARCH_MODEL)), sut.searchStateFlow.value)
    }
    */
}

object FakeSearchRepository : SearchRepository {
    override fun getListings(): Flow<SearchModel> =
        flow {
            emit(A_SEARCH_MODEL)
        }
}

private val A_SEARCH_MODEL = SearchModel(
    listOf(ListingItem("Cazan", 60f, "10 €", 2, ""))
)