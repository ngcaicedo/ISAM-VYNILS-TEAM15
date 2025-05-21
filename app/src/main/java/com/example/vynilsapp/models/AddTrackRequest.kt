package com.example.vynilsapp.models

import com.google.gson.annotations.SerializedName

data class AddTrackRequest(
    @SerializedName("track")
    val track: String,
    @SerializedName("album")
    val album: String
)