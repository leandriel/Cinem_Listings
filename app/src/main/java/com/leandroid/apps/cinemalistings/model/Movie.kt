package com.leandroid.apps.cinemalistings.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var title: String,
    var fullTitle: String,
    var year: Int,
    var releaseState: Date?,
    var image: String,
    var imDbRating: String,
    var genres: String,
    var plot: String,

){
    constructor() : this("", "", "", -1, null, "", "", "","")
}

