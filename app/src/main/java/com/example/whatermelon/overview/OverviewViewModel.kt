package com.example.whatermelon.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatermelon.network.ActivityApi
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    init {
        getRandomActivity()
    }

    private fun getRandomActivity() {
        viewModelScope.launch {
            try {
                ActivityApi.retrofitService.getRandomData()
                _status.value = "Success: Random activity retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}