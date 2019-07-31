package com.marito.rappitest.models

import androidx.lifecycle.LiveData

/**
 * VideoResult from a request
 * @property data hold query data
 * @property networkErrors network error state
 */
data class VideoResult(
    val data: LiveData<List<Video>>,
    val networkErrors: LiveData<String>
)