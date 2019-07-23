package com.marito.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marito.rappitest.models.Movie

class MovieDetailFactory(private val movie: Movie) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java))
            return MovieDetailViewModel(movie) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}