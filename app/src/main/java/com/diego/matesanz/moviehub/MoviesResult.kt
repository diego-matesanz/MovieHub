package com.diego.matesanz.moviehub

import com.google.gson.annotations.SerializedName

data class MoviesResult(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)