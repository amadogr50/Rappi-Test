package com.marito.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marito.rappitest.repositories.MovieRepository

class MoviesFactory(
    private val repository: MovieRepository,
    private val kind: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
            return MoviesViewModel(repository, kind) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
