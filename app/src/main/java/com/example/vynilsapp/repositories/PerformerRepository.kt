package com.example.vynilsapp.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.network.NetworkServiceAdapter

class PerformerRepository(val application: Application) {
    fun refreshData(callback: (List<Performer>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getPerformers({
            //Guardar los coleccionistas de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }
}