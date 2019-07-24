package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.repositories.MovieRepository
import com.marito.rappitest.webservices.ApiFactory

class MovieDetailViewModel(private val movieId : Int) : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository(ApiFactory.tmdbApi)

    val movie: LiveData<Movie>
        get() = movieRepository.getMovie(movieId)

}