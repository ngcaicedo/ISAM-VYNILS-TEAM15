package com.example.vynilsapp.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.CreateAlbumRequest
import com.example.vynilsapp.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshData(): List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun getAlbum(id: Int): Album {
        return NetworkServiceAdapter.getInstance(application).getAlbum(id)
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