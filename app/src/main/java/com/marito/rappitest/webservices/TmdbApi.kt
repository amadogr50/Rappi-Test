package com.marito.rappitest.webservices

import com.marito.rappitest.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface TmdbApi {
/*
    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int): Call<Movie>>*/

    @GET("movie/popular")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Call<MovieResponse>
}