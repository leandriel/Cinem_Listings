package com.leandroid.apps.cinemalistings.data.repository

import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.model.Movie
import retrofit2.Response

class DetailsRepository (private val apiManager: APIManager) {
    suspend fun getMovieDetails(id: String): Response<Movie> {
        return apiManager.getMovieDetails(id)
    }
}