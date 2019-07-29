package com.marito.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marito.rappitest.repositories.MovieRepository

class SearchMoviesFactory(
    private val repository: MovieRepository,
    private val query: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMoviesViewModel::class.java))
            return SearchMoviesViewModel(repository, query) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}