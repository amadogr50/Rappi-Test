package com.marito.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marito.rappitest.models.Movie

class MoviesFactory(private val movies: List<Movie>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
            return MoviesViewModel(movies) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}