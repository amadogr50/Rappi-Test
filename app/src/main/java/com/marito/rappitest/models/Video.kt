package com.marito.rappitest.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movie_id")
    )]
)
data class Video(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "movie_id")
    var movieId: Int?,
    @ColumnInfo(name = "iso_3166_1")
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @ColumnInfo(name = "iso_639_1")
    @SerializedName("iso_639_1")
    val iso6391: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)