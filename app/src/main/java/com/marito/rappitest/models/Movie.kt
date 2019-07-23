package com.marito.rappitest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String,

    val title: String,

    val popularity: Float,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String,

//    @SerializedName("genre_ids")
//    @ColumnInfo(name = "genre_ids")
//    val genreIds: List<Int>,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    val adult: Boolean,

    val overview: String,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String
)