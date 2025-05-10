package com.example.vynilsapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.repositories.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :  AndroidViewModel(application) {
    private val albumsRepository = AlbumRepository(application)
    private val _albums = MutableLiveData<List<Album>>()

    val albums: LiveData<List<Album>>
        get() = _albums

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
            viewModelScope.launch(ioDispatcher){
                val data = albumsRepository.refreshData()
                _albums.postValue(data)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e: Exception) {
            Log.d("AlbumViewModel", "Error: ${e.message}")
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
