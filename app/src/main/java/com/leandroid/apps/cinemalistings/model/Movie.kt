package com.leandroid.apps.cinemalistings.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("fullTitle") val fullTitle: String,
    @SerializedName("year") val year: Int,
    @SerializedName("releaseState") val releaseState: String,
    @SerializedName("poster_path") val images: String,
){
    var urlImage : String = ""
        get() = "https://m.media-amazon.com/images/M/${images}"
}
