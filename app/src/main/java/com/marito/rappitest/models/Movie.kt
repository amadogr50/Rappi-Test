package com.marito.rappitest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Float = 0f,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    var voteCount: Int = 0,

    @ColumnInfo(name = "video")
    var video: Boolean = false,

    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    var mediaType: String? = "",

    var title: String = "",

    var popularity: Float = 0f,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = "",

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    var originalLanguage: String,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    var originalTitle: String = "",

//    @SerializedName("genre_ids")
//    @ColumnInfo(name = "genre_ids")
//    var genreIds: List<Int>,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = "",

    var adult: Boolean = false,

    var overview: String = "",

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String = ""
)