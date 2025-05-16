package com.example.vynilsapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynilsapp.models.Collector
import com.example.vynilsapp.repositories.CollectorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectorViewModel(application: Application, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :  AndroidViewModel(application) {
    private val collectorsRepository = CollectorRepository(application)
    private val _collectors = MutableLiveData<List<Collector>>()

    val collectors: LiveData<List<Collector>>
        get() = _collectors

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
                val data = collectorsRepository.refreshData()
                _collectors.postValue(data)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            Log.d("CollectorViewModel", "Error: ${e.message}")
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
} 