package com.marito.rappitest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marito.rappitest.db.TmdbLocalCache
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.MovieResult
import com.marito.rappitest.webservices.TmdbApi
import com.marito.rappitest.webservices.getMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun getMovie(movieId: Int): LiveData<Movie> {
        val data = MutableLiveData<Movie>()
        tmdbApi.getMovie(movieId).enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                TODO()
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                data.postValue(response.body())
            }
        })
        return data
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

    fun requestMore(kind: Int) {
        requestAndSaveMovies(kind)
    }

    private fun requestAndSaveMovies(kind: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        getMovies(
            tmdbApi,
            kind,
            lastRequestedPage, { movies ->
                cache.insertMovies(movies) {
                    lastRequestedPage++
                    isRequestInProgress = false
                }
            }, { error ->
                networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }
}