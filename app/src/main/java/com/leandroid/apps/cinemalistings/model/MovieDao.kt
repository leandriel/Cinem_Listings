package com.leandroid.apps.cinemalistings.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<Movie>)

    @Query("SELECT * FROM movies ORDER BY year DESC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getById(id: String): Movie
}