package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.repositories.MovieRepository

class MovieDetailViewModel(
    private val movieRepository: MovieRepository,
    private val movieId: Int
) : ViewModel() {

    val movie: LiveData<Movie>
        get() = movieRepository.getMovie(movieId)

}