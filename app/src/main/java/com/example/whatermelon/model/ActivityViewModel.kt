package com.example.whatermelon.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whatermelon.repository.MainRepository
import kotlinx.coroutines.*

class ActivityViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _activity = MutableLiveData<Activity>()
    val activity: LiveData<Activity> = _activity

    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        reset()
    }

    fun getRandomActivity() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getRandomActivity()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _activity.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun reset() {
        _activity.postValue(Activity("", "", ""))
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}