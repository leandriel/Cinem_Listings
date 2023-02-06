package com.leandroid.apps.cinemalistings.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.apps.cinemalistings.domain.GetMoviesUseCase
import com.leandroid.apps.cinemalistings.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    //TODO: sealed class

    val movies: LiveData<MutableList<Movie>>
        get() = _movies
    private val _movies = MutableLiveData<MutableList<Movie>>()
    val isError: LiveData<Boolean>
        get() = _isError
    private val _isError = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            getMoviesUseCase()?.let { movies ->
                _isError.value = false
                _movies.value = movies.toMutableList()
            } ?: run {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }
}