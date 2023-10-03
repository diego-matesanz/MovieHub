package com.diego.matesanz.moviehub

data class MoviesResult(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
