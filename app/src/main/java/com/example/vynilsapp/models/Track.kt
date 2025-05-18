package com.example.vynilsapp.models

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id")
    val trackId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("album")
    val album: Album,
)
