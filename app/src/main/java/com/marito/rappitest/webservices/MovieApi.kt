package com.marito.rappitest.webservices

import com.marito.rappitest.models.Movie
import dagger.Component
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

@Component
interface MovieApi {

    @GET("/3/movie/{movie_id}?api_key={api_key}")
    fun getMovie(@Path("movie_id") movieId: Int, @Path("api_key") apiKey: String): Call<Movie>

    @GET("3/movie/popular?api_key={api_key}")
    fun getPopularMovies(@Path("api_key") apiKey: String): Call<List<Movie>>

    @GET("3/movie/top_rated?api_key={api_key}")
    fun getTopRatedMovies(@Path("api_key") apiKey: String): Call<List<Movie>>

    @GET("3/movie/upcoming?api_key={api_key}")
    fun getUpcomingMovies(@Path("api_key") apiKey: String): Call<List<Movie>>
}