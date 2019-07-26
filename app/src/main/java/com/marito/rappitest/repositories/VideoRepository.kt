package com.marito.rappitest.repositories

import androidx.lifecycle.MutableLiveData
import com.marito.rappitest.db.TmdbLocalCache
import com.marito.rappitest.models.VideoResult
import com.marito.rappitest.webservices.TmdbApi
import com.marito.rappitest.webservices.getVideosOfMovie

class VideoRepository constructor(
    private val tmdbApi: TmdbApi,
    private val cache: TmdbLocalCache
) {
    /**
     * LiveData of network errors.
     */
    private val networkErrors = MutableLiveData<String>()

    /**
     * Avoids triggering multiple requests in the same time
     */
    private var isRequestInProgress = false

    fun getVideosOfMovie(movieId: Int): VideoResult {
        requestAndSaveVideos(movieId)

        //Get data from local cache
        val data = cache.getVideosOfMovie(movieId)

        return VideoResult(data, networkErrors)
    }

    private fun requestAndSaveVideos(movieId: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        getVideosOfMovie(
            tmdbApi,
            movieId, { videos ->
                cache.insertVideos(videos) {
                    isRequestInProgress = false
                }
            }, { error ->
                networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }
}