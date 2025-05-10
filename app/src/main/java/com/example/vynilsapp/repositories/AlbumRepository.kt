package com.example.vynilsapp.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.CreateAlbumRequest
import com.example.vynilsapp.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbums({
            //Guardar los coleccionistas de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }

    fun getAlbum(id: String, onComplete: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbum(id, onComplete, onError)
    }

    fun createAlbum(
        albumRequest: CreateAlbumRequest,
        onComplete: (Album) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkServiceAdapter.getInstance(application)
            .createAlbum(albumRequest, onComplete, onError)
    }
}