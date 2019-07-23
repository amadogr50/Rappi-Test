package com.marito.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoviesFactory(private val kind: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
            return MoviesViewModel(kind) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
