package com.diego.matesanz.moviehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diego.matesanz.moviehub.ui.theme.MovieHubTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    Scaffold(
                        topBar = { TopAppBar(title = { Text(text = "Movies") }) }
                    ) { padding ->
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            modifier = Modifier.padding(padding),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            items(movies.value) { movie ->
                                MovieItem(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MovieItem(movie: Movie) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185/${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(2 / 3f)
            )
            Text(
                text = movie.title,
                modifier = Modifier
                    .padding(8.dp)
                    .height(48.dp),
                maxLines = 2
            )
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}