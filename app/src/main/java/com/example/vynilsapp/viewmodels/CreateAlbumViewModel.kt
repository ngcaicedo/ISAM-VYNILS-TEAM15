package com.example.vynilsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.CreateAlbumRequest
import com.example.vynilsapp.repositories.AlbumRepository

class CreateAlbumViewModel(application: Application) : AndroidViewModel(application) {
    
    private val albumRepository = AlbumRepository(application)
    
    // Para manejar el estado de la creación
    private val _albumCreated = MutableLiveData<Album>()
    val albumCreated: LiveData<Album>
        get() = _albumCreated
    
    // Para manejar errores
    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    
    // Para manejar mensajes de error
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    
    fun createAlbum(name: String, cover: String, releaseDate: String, description: String, genre: String, recordLabel: String) {
        _isLoading.value = true
        _eventNetworkError.value = false
        
        val albumRequest = CreateAlbumRequest(
            name = name,
            cover = cover,
            releaseDate = releaseDate,
            description = description,
            genre = genre,
            recordLabel = recordLabel
        )
        
        albumRepository.createAlbum(albumRequest,
            { album ->
                _albumCreated.value = album
                _isLoading.value = false
            },
            { error ->
                _eventNetworkError.value = true
                _errorMessage.value = error.message ?: "Error desconocido"
                _isLoading.value = false
            }
        )
    }
    
    fun onNetworkErrorShown() {
        _eventNetworkError.value = false
    }
    
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
} 