package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.MovieResponse
import com.marito.rappitest.models.MovieResult
import com.marito.rappitest.repositories.MovieRepository
import com.marito.rappitest.util.Constants
import com.marito.rappitest.util.Injection
import com.marito.rappitest.webservices.ApiFactory

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val kind: Int
) : ViewModel() {

    val movies: LiveData<List<Movie>>
        get() = movieRepository.getMovies(kind).data

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            movieRepository.requestMore(kind)
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

}