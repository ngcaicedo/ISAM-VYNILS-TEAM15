package com.example.vynilsapp.api

import com.example.vynilsapp.dataResponses.AlbumCatalogResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/albums")
    suspend fun getAlbumCatalog(): Response<List<AlbumCatalogResponse>>
}