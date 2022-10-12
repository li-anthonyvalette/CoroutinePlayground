package com.coroutine.coroutineplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coroutine.coroutineplayground.features.search.model.ListingItem
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchScreen
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchViewModel
import com.coroutine.coroutineplayground.ui.theme.CoroutinePlaygroundTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoroutinePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val searchState =
                        searchViewModel.searchStateLiveData.observeAsState(SearchScreen.Loading)

                    when (searchState.value) {
                        is SearchScreen.Success -> {
                            DisplayResults(
                                (searchState.value as SearchScreen.Success).searchModel
                            ) { searchViewModel.fetchListings() }
                        }
                        SearchScreen.Loading -> {
                            Greeting("Android loading")
                        }
                        SearchScreen.Error -> {
                            Column {
                                Greeting("Android error")
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun retry() {
        searchViewModel.fetchListings()
    }
}

@Composable
fun DisplayResults(searchModel: SearchModel, onRefresh: () -> Unit) {
    SwipeRefresh(
        state = SwipeRefreshState(false),
        onRefresh = { onRefresh.invoke() },
    ) {
        LazyColumn {
            items(searchModel.items) { listingItem ->
                DisplayListingItem(listingItem)
            }
        }
    }
}

@Composable
fun DisplayListingItem(listingItem: ListingItem) {
    Card {
        Text(text = listingItem.city, style = MaterialTheme.typography.titleLarge)
        Text(text = "${listingItem.price} €", style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutinePlaygroundTheme {
        Greeting("Android")
    }
}