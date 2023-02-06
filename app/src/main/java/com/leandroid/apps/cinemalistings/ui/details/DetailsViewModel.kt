package com.leandroid.apps.cinemalistings.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.apps.cinemalistings.domain.GetDetailsUseCase
import com.leandroid.apps.cinemalistings.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase
) : ViewModel() {

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
            getDetailsUseCase(id).let { movie ->
                if (movie != null) {
                    _isError.value = false
                    _movie.value = movie
                } else {
                    _isError.value = true
                }
                _isLoading.value = false
            }
        }
    }
}