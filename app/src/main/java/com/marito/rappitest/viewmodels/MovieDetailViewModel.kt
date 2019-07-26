package com.marito.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marito.rappitest.models.Movie
import com.marito.rappitest.models.Video
import com.marito.rappitest.repositories.MovieRepository
import com.marito.rappitest.repositories.VideoRepository

class MovieDetailViewModel(
    private val videoRepository: VideoRepository,
    private val movieRepository: MovieRepository,
    private val movieId: Int
) : ViewModel() {

    val movie: LiveData<Movie>
        get() = movieRepository.getMovie(movieId)

    val videos: LiveData<List<Video>>
        get() = videoRepository.getVideosOfMovie(movieId).data

}