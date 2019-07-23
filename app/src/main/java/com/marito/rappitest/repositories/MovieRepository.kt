package com.marito.rappitest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.MovieResponse
import com.marito.rappitest.webservices.TmdbApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class MovieRepository constructor(
    private val tmdbApi: TmdbApi
    //private val movieDao: MovieDao
) {

    fun getPopularMovies(): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        tmdbApi.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                TODO()
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                data.postValue(response.body()!!.results)
            }
        })

        return data
    }

    fun getTopRatedMovies(): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        tmdbApi.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                TODO()
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                data.postValue(response.body()!!.results)
            }
        })

        return data
    }

    fun getUpcomingMovies(): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        tmdbApi.getUpcomingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                TODO()
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                data.postValue(response.body()!!.results)
            }
        })

        return data
    }


/*
    fun getMovie(movieId: Int, apiKey: String): LiveData<Movie> {
        refreshMovie(movieId, apiKey)
        return movieDao.getMovie(movieId)
    }

    private fun refreshMovie(movieId: Int, apiKey : String) {
        executor.execute {
            val movieExist = movieDao.hasMovie(movieId)
            if (movieExist == 0) {
                val response = tmdbApi.getMovie(movieId, apiKey).enqueue(Callback<Movie> {

                })

                movieDao.insert(response.body()!!)
            }
        }
    }*/

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}