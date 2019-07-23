package com.marito.rappitest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marito.rappitest.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}