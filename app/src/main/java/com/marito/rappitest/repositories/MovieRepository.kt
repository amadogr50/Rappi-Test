package com.marito.rappitest.repositories

import androidx.lifecycle.LiveData
import com.marito.rappitest.room.MovieDao
import com.marito.rappitest.models.Movie
import com.marito.rappitest.webservices.MovieApi
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val executor: Executor,
    private val movieDao: MovieDao
) {

    fun getMovie(movieId: Int, apiKey: String): LiveData<Movie> {
        refreshMovie(movieId, apiKey)
        return movieDao.getMovie(movieId)
    }

    private fun refreshMovie(movieId: Int, apiKey : String) {
        executor.execute {
            val movieExist = movieDao.hasMovie(movieId)
            if (movieExist == 0) {
                val response = movieApi.getMovie(movieId, apiKey).execute()

                movieDao.insert(response.body()!!)
            }
        }
    }

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}