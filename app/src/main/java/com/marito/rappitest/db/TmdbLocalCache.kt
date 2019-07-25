package com.marito.rappitest.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marito.rappitest.models.Movie
import com.marito.rappitest.util.Constants
import java.util.concurrent.Executor

const val TAG = "TmdbLocalCache"

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the correct executor.
 */
class TmdbLocalCache (
    private val movieDao: MovieDao,
    private val ioExecutor: Executor
) {


    /**
     * Insert a list of repos in the database on a background thread
     */
    fun insert(movies: List<Movie>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d(TAG, "Inserting ${movies.size} movies")
            movieDao.insert(movies)
            insertFinished()
        }
    }

    /**
     * Request a LiveData<List<Movie>> from DAO depending on the kind.
     * @param kind defines request's movie list (Popular, Top Rated, Upcoming)
     */
    fun getMovies(kind: Int): LiveData<List<Movie>> {
        return when(kind) {
            Constants.PopularMovies -> movieDao.getPopularMovies()
            Constants.TopRatedMovies -> movieDao.getTopRatedMovies()
            Constants.UpcomingMovies -> movieDao.getUpcomingMovies()
            else -> movieDao.getPopularMovies()
        }
    }
}