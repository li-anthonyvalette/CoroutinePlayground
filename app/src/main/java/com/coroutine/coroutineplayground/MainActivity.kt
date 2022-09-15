package com.coroutine.coroutineplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coroutine.coroutineplayground.features.search.model.SearchModel
import com.coroutine.coroutineplayground.features.search.viewmodel.SearchViewModel
import com.coroutine.coroutineplayground.ui.theme.CoroutinePlaygroundTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue

import androidx.compose.runtime.livedata.observeAsState

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
                    val searchModel by searchViewModel.fetchListings().collectAsState(initial = SearchModel(emptyList()))

                    val city = if (searchModel.items.isNotEmpty()) searchModel.items[0].city else "toto"
                    Greeting("Android $city")
                }
            }
        }
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