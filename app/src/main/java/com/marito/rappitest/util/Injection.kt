package com.marito.rappitest.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.marito.rappitest.db.MovieDatabase
import com.marito.rappitest.db.TmdbLocalCache
import com.marito.rappitest.repositories.MovieRepository
import com.marito.rappitest.repositories.VideoRepository
import com.marito.rappitest.viewmodels.MovieDetailFactory
import com.marito.rappitest.viewmodels.MoviesFactory
import com.marito.rappitest.webservices.ApiFactory
import java.util.concurrent.Executors

object Injection {

    /**
     * Creates an instance of [TmdbLocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): TmdbLocalCache {
        val database = MovieDatabase.getInstance(context)
        return TmdbLocalCache(database.movieDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideMovieRepository(context: Context): MovieRepository {
        return MovieRepository(ApiFactory.tmdbApi, provideCache(context))
    }

    private fun provideVideoRepository(context: Context) : VideoRepository {
        return VideoRepository(ApiFactory.tmdbApi, provideCache(context))
    }

    fun provideMoviesFactory(context: Context, kind : Int): ViewModelProvider.Factory {
        return MoviesFactory(provideMovieRepository(context), kind)
    }

    fun provideMovieDetailFactory(context: Context, movieId: Int): ViewModelProvider.Factory {
        return MovieDetailFactory(provideVideoRepository(context), provideMovieRepository(context), movieId)
    }
}