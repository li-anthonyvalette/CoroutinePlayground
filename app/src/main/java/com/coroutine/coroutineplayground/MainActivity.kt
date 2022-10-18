package com.coroutine.coroutineplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.coroutine.coroutineplayground.features.search.model.ListingItem
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchScreenState
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchViewModel
import com.coroutine.coroutineplayground.ui.theme.CoroutinePlaygroundTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoroutinePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Coroutine Playground") },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White
                            )
                        )
                    },
                    content = { padding ->
                        Search(padding, searchViewModel)
                    }
                )
            }
        }
    }
}

@Composable
fun Search(padding: PaddingValues, searchViewModel: SearchViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding()),
        color = MaterialTheme.colorScheme.background
    ) {
        val searchState =
            searchViewModel.searchStateLiveData.observeAsState(SearchScreenState.Loading)

        SwipeRefresh(
            state = SwipeRefreshState(searchState.value == SearchScreenState.Loading),
            onRefresh = { searchViewModel.fetchListings() },
        ) {
            when (searchState.value) {
                is SearchScreenState.Success -> {
                    DisplayResults(
                        (searchState.value as SearchScreenState.Success).searchModel
                    )
                }
                SearchScreenState.Loading -> {
                    Text("Loading")
                }
                SearchScreenState.Error -> {
                    Column {
                        Text("Error")
                        Button(onClick = { searchViewModel.fetchListings() }) {
                            Text(text = "Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayResults(searchModel: SearchModel) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(searchModel.items) { listingItem ->
            DisplayListingItem(listingItem)
        }
    }
}

@Composable
fun DisplayListingItem(listingItem: ListingItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(listingItem.url)
                .crossfade(true)
                .build(),
            placeholder = ColorPainter(Color.LightGray),
            error = ColorPainter(Color.LightGray),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = listingItem.city,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = listingItem.price,
            style = MaterialTheme.typography.titleSmall
        )
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