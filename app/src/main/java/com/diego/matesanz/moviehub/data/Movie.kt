package com.diego.matesanz.moviehub.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favourite: Boolean = false
)
