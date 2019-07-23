package com.marito.rappitest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.marito.rappitest.models.Movie

@Dao
interface MovieDao {
    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<Movie>

    @Query("SELECT COUNT(*) FROM movie WHERE id = :movieId")
    fun hasMovie(movieId: Int): Int

    @Query("SELECT * FROM movie") //ToDo: Implement condition
    fun getPopularMovies()

    @Query("SELECT * FROM movie") //ToDo: Implement condition
    fun getTopRatedMovies()

    @Query("SELECT * FROM movie") //ToDo: Implement condition
    fun getUpcomingMovies()
}