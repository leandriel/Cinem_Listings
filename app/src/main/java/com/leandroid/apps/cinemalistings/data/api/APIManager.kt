package com.leandroid.apps.cinemalistings.data.api

import com.leandroid.apps.cinemalistings.data.api.RetrofitService.Companion.getRetrofit
import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import com.leandroid.apps.cinemalistings.model.Movie
import retrofit2.Response
import retrofit2.Retrofit

class APIManager {
    suspend fun getMovies(): Response<MovieDTO> {
        return getRetrofitInstance().getMovies()
    }

    suspend fun getMovieDetails(id: Int):Response<Movie>  {
        return getRetrofitInstance().getMovieDetails(id)
    }

    private fun getRetrofitInstance(): APIService {
        return Retrofit.getRetrofit().create(APIService::class.java)
    }
}