package com.marito.rappitest.webservices

import android.util.Log
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.MovieResponse
import com.marito.rappitest.models.Video
import com.marito.rappitest.models.VideoResponse
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

fun getMovie(
    tmdbApi: TmdbApi,
    movieId: Int,
    onSuccess: (movie: Movie) -> Unit,
    onError: (error: String) -> Unit
) {
    tmdbApi.getMovie(movieId).enqueue(
        object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d(TAG, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val movie = response.body()
                    onSuccess(movie!!)
                } else {
                    onError(response.errorBody()?.string() ?: "Unkown error")
                }
            }
        }
    )
}

fun getVideosOfMovie(
    tmdbApi: TmdbApi,
    movieId: Int,
    onSuccess: (videos: List<Video>) -> Unit,
    onError: (error: String) -> Unit
) {
    val request = tmdbApi.getVideosOfMovie(movieId)
    Log.d(TAG, "movieId: $movieId")

    request.enqueue(
        object : Callback<VideoResponse> {
            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.d(TAG, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val videos = response.body()?.results ?: emptyList()
                    //Add movieId to each video
                    for(video in videos) {
                        video.movieId = movieId
                    }
                    onSuccess(videos)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        })

}

fun searchMovie(
    tmdbApi: TmdbApi,
    query: String,
    page: Int,
    onSuccess: (movies: List<Movie>) -> Unit,
    onError: (error: String) -> Unit
) {
    val request = tmdbApi.searchMovie(query, page)
    Log.d(TAG, "query: $query")

    request.enqueue(
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
        })

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

    @GET("movie/{movie_id}/videos")
    fun getVideosOfMovie(@Path("movie_id") movieId: Int) : Call<VideoResponse>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Call<MovieResponse>
}