package com.diego.matesanz.moviehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diego.matesanz.moviehub.ui.theme.MovieHubTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContent {
            MovieHubTheme {

                val movies = produceState(initialValue = emptyList<Movie>()) {
                    value = Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(MoviesService::class.java)
                        .getMovies()
                        .results
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LazyVerticalGrid(columns = GridCells.Adaptive(180.dp)) {
                        items(movies.value) { movie ->
                            Text(movie.title)
                        }
                    }
                }
            }
        }
    }

    companion object {
        lateinit var instance: MainActivity
            private set

        private val TAG = MainActivity::class.java.simpleName
    }
}