package com.example.vynilsapp.repositories
import android.app.Application
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.network.NetworkServiceAdapter

class PerformerRepository(val application: Application) {
    suspend fun refreshData(): List<Performer> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getPerformers()
    }

    suspend fun getPerformer(id: Int, typePerformer: String): Performer {
        return NetworkServiceAdapter.getInstance(application).getPerformer(id, typePerformer)
    }
}