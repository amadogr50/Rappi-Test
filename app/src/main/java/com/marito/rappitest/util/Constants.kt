package com.marito.rappitest.util

import com.marito.rappitest.BuildConfig

object Constants {
    const val tmbdApiKey = BuildConfig.TMDB_API_KEY
    const val youtubeApiKey = BuildConfig.YOUTUBE_API_KEY

    const val tmdbImageBaseUrl = "https://image.tmdb.org/t/p/w500/"
    const val tmdbBaseUrl = "https://api.themoviedb.org/3/"

    //Movies Kind
    const val PopularMovies = 0
    const val TopRatedMovies = 1
    const val UpcomingMovies = 2
}