package com.diego.matesanz.moviehub.data.remote

import com.google.gson.annotations.SerializedName

data class MoviesResult(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<ServerMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
