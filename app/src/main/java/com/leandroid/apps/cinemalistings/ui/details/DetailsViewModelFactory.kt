package com.leandroid.apps.cinemalistings.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroid.apps.cinemalistings.data.repository.DetailsRepository

class DetailsViewModelFactory(private val repository: DetailsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DetailsRepository::class.java)
            .newInstance(repository)
    }
}