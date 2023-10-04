package com.diego.matesanz.moviehub

import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=${ApiKey.apiKey}")
    suspend fun getMovies(): MoviesResult
}
