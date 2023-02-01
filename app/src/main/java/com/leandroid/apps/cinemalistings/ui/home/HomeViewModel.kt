package com.leandroid.apps.cinemalistings.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.apps.cinemalistings.data.repository.MovieRepository
import com.leandroid.apps.cinemalistings.model.Movie
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel(){
    val movies: LiveData<MutableList<Movie>>
        get() = _movies
    private val _movies = MutableLiveData<MutableList<Movie>>()
    val isError: LiveData<Boolean>
        get() = _isError
    private val _isError = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    fun getMovies(){
        viewModelScope.launch {
            _isLoading.value = true
            repository.getMovies().let { response ->
                if (response.isSuccessful) {
                    _isError.value = false
                    _movies.value = response.body()?.let {
                        it.items
                    } ?: mutableListOf()
                } else {
                    _isError.value = true
                }
                _isLoading.value = false
            }
        }
    }
}