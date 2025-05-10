package com.example.vynilsapp.repositories
import android.app.Application
import android.util.Log
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.network.CacheManager
import com.example.vynilsapp.network.NetworkServiceAdapter

class PerformerRepository(val application: Application) {
    suspend fun refreshData(): List<Performer> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getPerformers()
    }

    suspend fun getPerformer(id: Int, typePerformer: String): Performer {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getPerformerDetails(id)
        return if (potentialResp.performerId == 0) {
            Log.d("Cache decision", "get from network")
            val performer = NetworkServiceAdapter.getInstance(application).getPerformer(id, typePerformer)
            CacheManager.getInstance(application.applicationContext).addPerformerDetails(id, performer)
            performer
        } else {
            Log.d("Cache decision", "return elements from cache")
            potentialResp
        }
    }
}