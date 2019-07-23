package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.repositories.MovieRepository
import com.marito.rappitest.util.Constants
import com.marito.rappitest.webservices.ApiFactory

class MoviesViewModel(private val kind: Int) : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository(ApiFactory.tmdbApi)

    val movies: LiveData<List<Movie>>
        get() = when (kind) {
            Constants.PopularMovies -> movieRepository.getPopularMovies()
            Constants.TopRatedMovies -> movieRepository.getTopRatedMovies()
            Constants.UpcomingMovies -> movieRepository.getUpcomingMovies()
            else -> MutableLiveData()
        }

}