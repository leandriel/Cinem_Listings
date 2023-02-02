package com.leandroid.apps.cinemalistings.data.repository

import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.dto.MovieDTO
import com.leandroid.apps.cinemalistings.model.Movie
import com.leandroid.apps.cinemalistings.model.MovieDao
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import okhttp3.Interceptor.Companion.invoke
import retrofit2.Response


class MovieRepository (
    private val apiManager: APIManager,
    private val movieDao: MovieDao
    ) {
    suspend fun getMovies(): List<Movie> {
        val result = apiManager.getMovies()

        if(result.isSuccessful){
            result.body()?.let {
                movieDao.insertMovies(it.items)
            }

        }
        return movieDao.getAllMovies()
    }
}