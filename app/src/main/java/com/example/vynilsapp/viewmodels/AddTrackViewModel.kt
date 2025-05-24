package com.example.vynilsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.repositories.AlbumRepository
import kotlinx.coroutines.launch

class AddTrackViewModel(application: Application) : AndroidViewModel(application) {
    private val albumRepository = AlbumRepository(application)

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _albumUpdated = MutableLiveData<Album>()
    val albumUpdated: LiveData<Album> get() = _albumUpdated

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val albums = albumRepository.refreshData()
                _albums.value = albums
                _isLoading.value = false
            } catch (e: Exception) {
                _eventNetworkError.value = true
                _errorMessage.value = e.message ?: "Failed to load albums"
                _isLoading.value = false
            }
        }
    }

    fun addTrackToAlbum(albumId: Int, trackName: String, trackDuration: String) {
        _isLoading.value = true
        _eventNetworkError.value = false

        albumRepository.addTrackToAlbum(
            albumId = albumId,
            trackName = trackName,
            trackDuration = trackDuration,
            onComplete = { updatedAlbum ->
                _albumUpdated.value = updatedAlbum
                _isLoading.value = false
            },
            onError = { error ->
                _eventNetworkError.value = true
                _errorMessage.value = error.message ?: "Error adding track"
                _isLoading.value = false
            }
        )
    }

    fun onNetworkErrorShown() {
        _eventNetworkError.value = false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddTrackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}