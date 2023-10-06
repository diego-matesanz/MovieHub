package com.diego.matesanz.moviehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.diego.matesanz.moviehub.data.MoviesRepository
import com.diego.matesanz.moviehub.data.local.LocalDataSource
import com.diego.matesanz.moviehub.data.local.MoviesDataBase
import com.diego.matesanz.moviehub.data.remote.RemoteDataSource
import com.diego.matesanz.moviehub.ui.screens.home.Home

class MainActivity : ComponentActivity() {

    private lateinit var db: MoviesDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            MoviesDataBase::class.java,
            "movies_db"
        ).build()

        val repository = MoviesRepository(
            localDataSource = LocalDataSource(db.moviesDao()),
            remoteDataSource = RemoteDataSource()
        )

        setContent {
            Home(repository)
        }
    }
}