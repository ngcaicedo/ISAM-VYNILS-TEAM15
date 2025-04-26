package com.example.vynilsapp.models

import com.google.gson.annotations.SerializedName

data class CreateAlbumRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("recordLabel")
    val recordLabel: String
)