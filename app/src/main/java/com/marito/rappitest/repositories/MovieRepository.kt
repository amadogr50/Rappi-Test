package com.marito.rappitest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marito.rappitest.db.TmdbLocalCache
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.MovieResult
import com.marito.rappitest.webservices.TmdbApi
import com.marito.rappitest.webservices.getMovies
import com.marito.rappitest.webservices.searchMovie
import javax.inject.Singleton

const val TAG = "MovieRepository"

/**
 * Repository class that works with local and remote data sources.
 */
@Singleton
class MovieRepository constructor(
    private val tmdbApi: TmdbApi,
    private val cache: TmdbLocalCache
) {

    /**
     * Keeps the last requested page. When the request is successful, increment the page number.
     */
    private var lastRequestedPage = 1

    /**
     * LiveData of network errors.
     */
    private val networkErrors = MutableLiveData<String>()


    /**
     * Avoids triggering multiple requests in the same time
     */
    private var isRequestInProgress = false


    /**
     * Gets a movie from movieID
     */
    fun getMovie(movieId: Int): LiveData<Movie> {
        //Get data from local cache
        return cache.getMovie(movieId)
    }

    /**
     * Gets movies depending on:
     * @param kind defines request's movie list (Popular, Top Rated, Upcoming)
     */
    fun getMovies(kind: Int): MovieResult {
        lastRequestedPage = 1
        requestAndSaveMovies(kind)

        //Get data from local cache
        val data = cache.getMovies(kind)

        return MovieResult(data, networkErrors)
    }

    fun searchMovie(query: String): MovieResult {
        lastRequestedPage = 1
        searchAndSaveMovies(query)

        //Get data from local cache
        val data = cache.searchMovie(query)

        return MovieResult(data, networkErrors)

    }

    fun requestMore(kind: Int) {
        requestAndSaveMovies(kind)
    }

    fun requestMore(query: String) {
        searchAndSaveMovies(query)
    }

    private fun refreshMovie(movieId: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        com.marito.rappitest.webservices.getMovie(
            tmdbApi,
            movieId, { movie ->
                cache.insertMovie(movie) {
                    isRequestInProgress = false
                }
            }, { error ->
                networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }

    private fun searchAndSaveMovies(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchMovie(
            tmdbApi,
            query,
            lastRequestedPage,
            onSuccess,
            onError
        )
    }

    private fun requestAndSaveMovies(kind: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        getMovies(
            tmdbApi,
            kind,
            lastRequestedPage,
            onSuccess,
            onError
        )
    }

    private val onSuccess: (movies: List<Movie>) -> Unit = { movies ->
        cache.insertMovies(movies) {
            lastRequestedPage++
            isRequestInProgress = false
        }

    }

    private val onError: (error: String) -> Unit = { error ->
        networkErrors.postValue(error)
        isRequestInProgress = false
    }
}