package com.example.vynilsapp.dataResponses

import com.google.gson.annotations.SerializedName

data class AlbumCatalogResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cover") val cover: String
)