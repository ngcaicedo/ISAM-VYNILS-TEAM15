package com.example.vynilsapp.models

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val albumId: Int,
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
    val recordLabel: String,
    @SerializedName("tracks")
    val tracks: MutableList<Track> = mutableListOf()
)