package com.example.vynilsapp.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.CreateAlbumRequest
import com.example.vynilsapp.network.CacheManager
import com.example.vynilsapp.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshData(): List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun getAlbum(id: Int): Album {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbumDetails(id)
        return if(potentialResp.albumId == 0){
            Log.d("Cache decision", "get from network")
            val comments = NetworkServiceAdapter.getInstance(application).getAlbum(id)
            CacheManager.getInstance(application.applicationContext).addAlbumDetails(id, comments)
            comments
        }
        else{
            Log.d("Cache decision", "return elements from cache")
            potentialResp
        }
    }

    fun createAlbum(
        albumRequest: CreateAlbumRequest,
        onComplete: (Album) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkServiceAdapter.getInstance(application)
            .createAlbum(albumRequest, onComplete, onError)
    }

    fun addTrackToAlbum(
        albumId: Int,
        trackName: String,
        trackDuration: String,
        onComplete: (Album) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkServiceAdapter.getInstance(application).addTrackToAlbum(
            albumId,
            trackName,
            trackDuration,
            { updatedAlbum ->
                CacheManager.getInstance(application.applicationContext)
                    .addAlbumDetails(updatedAlbum.albumId, updatedAlbum)
                onComplete(updatedAlbum)
            },
            onError
        )
    }
}