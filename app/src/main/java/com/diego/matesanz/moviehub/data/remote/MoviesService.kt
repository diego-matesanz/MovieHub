package com.diego.matesanz.moviehub.data.remote

import com.diego.matesanz.moviehub.ApiKey
import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=${ApiKey.apiKey}")
    suspend fun getMovies(): MoviesResult
}
