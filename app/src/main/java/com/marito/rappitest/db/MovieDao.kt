package com.marito.rappitest.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.marito.rappitest.models.Movie

@Dao
interface MovieDao {
    @Transaction
    fun updateData(movies: List<Movie>) {
        deleteAllUsers()
        insert(movies)
    }

    @Query("DELETE FROM movie")
    fun deleteAllUsers()

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<Movie>

    @Query("SELECT COUNT(*) FROM movie WHERE id = :movieId")
    fun hasMovie(movieId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getPopularMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    fun getTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    fun getUpcomingMovies(): LiveData<List<Movie>>
}