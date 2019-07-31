package com.marito.rappitest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity
@Fts4(contentEntity = Movie::class)
data class MovieFts(
    @ColumnInfo(name = "media_type")
    var mediaType: String? = "",
    var title: String = "",
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = "",
    @ColumnInfo(name = "original_language")
    var originalLanguage: String = "",
    @ColumnInfo(name = "original_title")
    var originalTitle: String = "",
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = "",
    var overview: String = "",
    @ColumnInfo(name = "release_date")
    var releaseDate: String = ""
)