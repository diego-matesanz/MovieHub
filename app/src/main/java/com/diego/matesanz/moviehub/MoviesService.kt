package com.diego.matesanz.moviehub

import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=6001b103e9cf4ad105cb25a8571de820")
    suspend fun getMovies(): MoviesResult
}
