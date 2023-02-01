package com.leandroid.apps.cinemalistings.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.apps.cinemalistings.data.repository.DetailsRepository
import com.leandroid.apps.cinemalistings.model.Movie
import kotlinx.coroutines.launch

class DetailsViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val movie: LiveData<Movie>
        get() = _movie
    private val _movie = MutableLiveData<Movie>()
    val isError: LiveData<Boolean>
        get() = _isError
    private val _isError = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    fun getDetailsMovie(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            detailsRepository.getMovieDetails(id).let { response ->
                if (response.isSuccessful) {
                    _isError.value = false
                    response.body()?.let {
                        _movie.value = it
                    } ?: run {
                        _isError.value = true
                    }
                } else {
                    _isError.value = true
                }
                _isLoading.value = false
            }
        }
    }
}