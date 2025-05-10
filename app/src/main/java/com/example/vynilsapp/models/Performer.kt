package com.example.vynilsapp.models

import com.google.gson.annotations.SerializedName

data class Performer (
    @SerializedName("id")
    val performerId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("creationDate")
    val creationDate: String? = null,
    @SerializedName("birthDate")
    val birthDate: String? = null
)