package com.leandroid.apps.cinemalistings.data.repository


import com.leandroid.apps.cinemalistings.model.Movie
import com.leandroid.apps.cinemalistings.model.MovieDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val movieDao: MovieDao) {
    suspend fun getMovieDetails(id: String): Movie? {
        return movieDao.getById(id)
    }
}