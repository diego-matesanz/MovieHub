package com.diego.matesanz.moviehub.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.moviehub.data.Movie
import com.diego.matesanz.moviehub.data.MoviesRepository
import com.diego.matesanz.moviehub.data.local.MoviesDao
import com.diego.matesanz.moviehub.data.local.toLocalMovie
import com.diego.matesanz.moviehub.data.local.toMovie
import com.diego.matesanz.moviehub.data.remote.MoviesService
import com.diego.matesanz.moviehub.data.remote.toLocalMovie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.requestMovies()

            repository.movies.collect {
                _state.value = UiState(movies = it)
            }
        }
    }

    fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie.copy(favourite = !movie.favourite))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}