package com.example.whatermelon.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.whatermelon.repository.MainRepository

class ActivityViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {
            ActivityViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}