package com.marito.rappitest.webservices

import android.util.Log
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.MovieResponse
import com.marito.rappitest.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TAG = "TmdbAPI"

/**
 * Triggers a request to TMDB API based on the following params:
 * @param kind defines request's movie list (Popular, Top Rated, Upcoming)
 * @param page defines request's page index
 *
 * The result of the request is handled by the implementation fo the functions passed as params
 * @param onSuccess function that defines how to handle the list of movies received
 * @param onError function that defines how to handle request failure
 */
fun getMovies(
    tmdbApi: TmdbApi,
    kind: Int,
    page: Int,
    onSuccess: (movies: List<Movie>) -> Unit,
    onError: (error: String) -> Unit
) {
    val request = when (kind) {
        Constants.PopularMovies -> tmdbApi.getPopularMovies(page)
        Constants.TopRatedMovies -> tmdbApi.getTopRatedMovies(page)
        Constants.UpcomingMovies -> tmdbApi.getUpcomingMovies(page)
        else -> null
    }

    Log.d(TAG, "kind: $kind, page: $page")

    request?.enqueue(
        object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    onSuccess(movies)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}

interface TmdbApi {

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int): Call<Movie>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int): Call<MovieResponse>
}