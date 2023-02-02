package com.leandroid.apps.cinemalistings.data.repository

import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import com.leandroid.apps.cinemalistings.model.MovieDao
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import okhttp3.Interceptor.Companion.invoke
import retrofit2.Response


class MovieRepository (
    private val apiManager: APIManager,
    private val movieDao: MovieDao
    ) {
    suspend fun getMovies(): Response<MovieDTO> {
        val result = apiManager.getMovies()

        if(result.isSuccessful){
                movieDao.insertMovies(result.body()!!.items)
        }
        return apiManager.getMovies()
    }
}