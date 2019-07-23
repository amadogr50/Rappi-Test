package com.marito.rappitest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    private val id: Int,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private val voteAverage: Float,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    private val voteCount: Int,

    @ColumnInfo(name = "video")
    private val video: Boolean,

    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    private val mediaType: String,

    private val title: String,

    private val popularity: Float,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private val posterPath: String,

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    private val originalLanguage: String,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    private val originalTitle: String,

    @SerializedName("genre_ids")
    @ColumnInfo(name = "genre_ids")
    private val genreIds: List<Int>,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private val backdropPath: String,

    private val adult: Boolean,

    private val overview: String,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private val releaseDate: String
)