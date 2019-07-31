package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.repositories.MovieRepository

class SearchMoviesViewModel(
    private val movieRepository: MovieRepository,
    private val query: String
) : ViewModel() {

    val movies: LiveData<List<Movie>>
        get() = movieRepository.searchMovie(query).data

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            movieRepository.requestMore(query)
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}