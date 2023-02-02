package com.leandroid.apps.cinemalistings.data.api

import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import com.leandroid.apps.cinemalistings.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET( "movies.json")
    suspend fun getMovies(@Query("key") apiKey: String = "cb03b960"): Response<MovieDTO>

}