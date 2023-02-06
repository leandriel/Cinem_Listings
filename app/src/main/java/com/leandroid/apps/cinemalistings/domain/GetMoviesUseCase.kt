package com.leandroid.apps.cinemalistings.domain

import com.leandroid.apps.cinemalistings.data.repository.MovieRepository
import com.leandroid.apps.cinemalistings.model.Movie
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie>?{
        return repository.getMovies()
    }
}