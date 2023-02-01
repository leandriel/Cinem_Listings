package com.leandroid.apps.cinemalistings.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: String,
    val title: String,
    val fullTitle: String,
    val year: Int,
    val releaseState: String,
    val image: String,
    val imDbRating: String,
    val genres: String,
    val plot: String,

){
    var urlImage : String = ""
        get() = "https://m.media-amazon.com/images/M/${image}"
}
