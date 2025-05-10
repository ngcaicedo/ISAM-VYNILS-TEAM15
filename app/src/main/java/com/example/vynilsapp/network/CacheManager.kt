package com.example.vynilsapp.network

import android.content.Context
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.Performer

class CacheManager(context: Context) {
    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var albumDetails: HashMap<Int, Album> = hashMapOf()
    fun addAlbumDetails(albumId: Int, detail: Album) {
        if (!albumDetails.containsKey(albumId)) {
            albumDetails[albumId] = detail
        }
    }
    fun getAlbumDetails(albumId: Int): Album {
        return albumDetails[albumId] ?: Album(0, "", "", "", "", "", "")
    }

    private var performerDetails: HashMap<Int, Performer> = hashMapOf()
    fun addPerformerDetails(performerId: Int, detail: Performer) {
        if (!performerDetails.containsKey(performerId)) {
            performerDetails[performerId] = detail
        }
    }
    fun getPerformerDetails(performerId: Int): Performer {
        return performerDetails[performerId] ?: Performer(0, "", "", "", "", "")
    }
}