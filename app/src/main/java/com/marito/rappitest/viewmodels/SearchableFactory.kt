package com.marito.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marito.rappitest.repositories.MovieRepository

class SearchableFactory(
    private val repository: MovieRepository,
    private val query: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchableViewModel::class.java))
            return SearchableViewModel(repository, query) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
