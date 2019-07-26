package com.marito.rappitest.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.Video
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
     * Insert a list of movies in the database on a background thread
     */
    fun insertMovies(movies: List<Movie>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d(TAG, "Inserting ${movies.size} movies")
            movieDao.insertMovies(movies)
            insertFinished()
        }
    }

    /**
     * Insert a list of videos in the database on a background thread
     */
    fun insertVideos(videos: List<Video>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d(TAG, "Inserting ${videos.size} movies")
            movieDao.insertVideos(videos)
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

    /**
     * Request a LiveData<List<Video>> from DAO depending of movieID.
     * @param movieId Movie's primary key
     */
    fun getVideosOfMovie(movieId: Int) : LiveData<List<Video>> {
        return movieDao.getVideosOfMovie(movieId)
    }
}