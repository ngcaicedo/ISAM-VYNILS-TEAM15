package com.example.vynilsapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.repositories.PerformerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerformerDetailViewModel(application: Application, performerId: Int, typePerformer: String, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :  AndroidViewModel(application) {
    private val performersRepository = PerformerRepository(application)

    private val _performerDetail = MutableLiveData<Performer>()
    val performerDetail: LiveData<Performer> get() = _performerDetail

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = performerId
    private val type:String = typePerformer

    init {
        refreshDataFromNetwork()
    }

    fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(ioDispatcher) {
                val data = performersRepository.getPerformer(id, type)
                Log.i("PerformerDetViewModel", "Data: $data")
                _performerDetail.postValue(data)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            Log.d("PerformerDetViewModel", "Error: ${e.message}")
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val performerId: Int, private val typePerformer: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerDetailViewModel(app, performerId, typePerformer ) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
