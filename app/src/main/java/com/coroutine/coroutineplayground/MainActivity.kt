package com.coroutine.coroutineplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchState
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchViewModel
import com.coroutine.coroutineplayground.ui.theme.CoroutinePlaygroundTheme
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
                        searchViewModel.searchStateLiveData.observeAsState(SearchState.Loading)

                    when (searchState.value) {
                        is SearchState.Success -> {
                            Greeting("Android success")
                        }
                        SearchState.Loading -> {
                            Greeting("Android loading")
                        }
                        SearchState.Error -> {
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