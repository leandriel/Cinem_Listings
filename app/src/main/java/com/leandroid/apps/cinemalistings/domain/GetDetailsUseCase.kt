package com.leandroid.apps.cinemalistings.domain

import com.leandroid.apps.cinemalistings.data.repository.DetailsRepository
import com.leandroid.apps.cinemalistings.model.Movie
import javax.inject.Inject


class GetDetailsUseCase @Inject constructor(private val repository: DetailsRepository) {
    suspend operator fun invoke(id:String): Movie?{
        return repository.getMovieDetails(id)
    }
}