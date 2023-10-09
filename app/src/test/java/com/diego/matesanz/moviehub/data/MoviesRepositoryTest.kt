package com.diego.matesanz.moviehub.data

import com.diego.matesanz.moviehub.data.local.LocalDataSource
import com.diego.matesanz.moviehub.data.remote.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking

class MoviesRepositoryTest {

    @Test
    fun `When DB is empty, server is called`() {
        val localDataSource = mock<LocalDataSource> { onBlocking { count() } doReturn 0 }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = MoviesRepository(localDataSource, remoteDataSource)

        runBlocking { repository.requestMovies() }

        verifyBlocking(remoteDataSource, times(1)) { getMovies() }
    }

    @Test
    fun `When DB is empty, movies are saved into DB`() {
        val expectedMovies = listOf(Movie(1, "title", "overview", "poster", false))
        val localDataSource = mock<LocalDataSource> { onBlocking { count() } doReturn 0 }
        val remoteDataSource = mock<RemoteDataSource> { onBlocking { getMovies() } doReturn expectedMovies }
        val repository = MoviesRepository(localDataSource, remoteDataSource)

        runBlocking { repository.requestMovies() }

        verifyBlocking(localDataSource, times(1)) { insertAll(expectedMovies) }
    }

    @Test
    fun `When DB is not empty, remote data source is not called`() {
        val localDataSource = mock<LocalDataSource> { onBlocking { count() } doReturn 1 }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = MoviesRepository(localDataSource, remoteDataSource)

        runBlocking { repository.requestMovies() }

        verifyBlocking(remoteDataSource, times(0)) { getMovies() }
    }

    @Test
    fun `When DB is not empty, movies are recovered from DB`() {
        val remoteMovies = listOf(Movie(1, "title", "overview", "poster", false))
        val localMovies = listOf(Movie(2, "title2", "overview2", "poster2", false))
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 1
            onBlocking { movies } doReturn flowOf(localMovies)
        }
        val remoteDataSource = mock<RemoteDataSource> {
            onBlocking { getMovies() } doReturn remoteMovies
        }
        val repository = MoviesRepository(localDataSource, remoteDataSource)

        val result = runBlocking {
            repository.requestMovies()
            repository.movies.first()
        }

        assertEquals(localMovies, result)
    }
}