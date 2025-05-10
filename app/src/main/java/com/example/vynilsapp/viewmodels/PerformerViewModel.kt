package com.example.vynilsapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.repositories.PerformerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerformerViewModel(application: Application, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :  AndroidViewModel(application) {
    private val performersRepository = PerformerRepository(application)
    private val _performers = MutableLiveData<List<Performer>>()

    val performers: LiveData<List<Performer>>
        get() = _performers

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(ioDispatcher) {
                val data = performersRepository.refreshData()
                _performers.postValue(data)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            Log.d("PerformerViewModel", "Error: ${e.message}")
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
