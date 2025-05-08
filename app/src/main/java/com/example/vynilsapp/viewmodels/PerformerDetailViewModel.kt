package com.example.vynilsapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.repositories.PerformerRepository

class PerformerDetailViewModel(application: Application) :  AndroidViewModel(application) {
    private val performersRepository = PerformerRepository(application)
    private val _performers = MutableLiveData<List<Performer>>()

    val performers: LiveData<List<Performer>>
        get() = _performers

    private val _performerDetail = MutableLiveData<Performer>()
    val performerDetail: LiveData<Performer> get() = _performerDetail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    fun refreshDataFromNetwork() {
        performersRepository.refreshData({
            _performers.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun getPerformerDetail(performerId: String, typePerformer: String) {
        Log.i("PerformerFragment", "PerformerDetailViewModel - typePerformer: ${typePerformer} | performerId: ${performerId}")
        performersRepository.getPerformer(performerId, typePerformer, { performer ->
            _performerDetail.postValue(performer)
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
