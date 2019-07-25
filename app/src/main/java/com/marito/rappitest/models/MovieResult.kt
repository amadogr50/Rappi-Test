package com.marito.rappitest.models

import androidx.lifecycle.LiveData

/**
 * MovieResult from a request
 * @property data hold query data
 * @property networkErrors network error state
 */
data class MovieResult(
    val data: LiveData<List<Movie>>,
    val networkErrors: LiveData<String>
)