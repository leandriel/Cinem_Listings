package com.leandroid.apps.cinemalistings.data.api

import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import retrofit2.Response
import javax.inject.Inject


class APIManager @Inject constructor (private val apiService: APIService) {

    suspend fun getMovies(): Response<MovieDTO> {
        return apiService.getMovies()
    }

}