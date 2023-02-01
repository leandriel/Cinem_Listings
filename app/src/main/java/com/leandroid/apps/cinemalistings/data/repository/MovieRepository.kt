package com.leandroid.apps.cinemalistings.data.repository

import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import retrofit2.Response


class MovieRepository (
    private val apiManager: APIManager,
    private val movieDataBase: MovieDataBase

    ) {
    suspend fun getMovies(): Response<MovieDTO> {
        movieDataBase.movieDao().insertMovies(response.body().items)
        return apiManager.getMovies()
    }
}