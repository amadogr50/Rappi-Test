package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.repositories.MovieRepository

class SearchableViewModel(
    private val movieRepository: MovieRepository,
    private val query: String
) : ViewModel() {

    val movies: LiveData<List<Movie>>
        get() = movieRepository.searchMovie(query).data
}