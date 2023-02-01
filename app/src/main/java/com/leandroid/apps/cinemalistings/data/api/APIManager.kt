package com.leandroid.apps.cinemalistings.data.api

import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import com.leandroid.apps.cinemalistings.model.Movie
import retrofit2.Response


class APIManager {
    suspend fun getMovies(): Response<MovieDTO> {
        return getRetrofitInstance().getMovies()
    }

    suspend fun getMovieDetails(id: String):Response<Movie>  {
        return getRetrofitInstance().getMovieDetails(id)
    }

    private fun getRetrofitInstance(): APIService {
        return RetrofitService.getRetrofit().create(APIService::class.java)
    }
}