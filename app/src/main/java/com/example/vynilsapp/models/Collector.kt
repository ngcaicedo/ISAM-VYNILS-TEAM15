package com.example.vynilsapp.models

import com.google.gson.annotations.SerializedName

data class Collector (
    @SerializedName("id")
    val collectorId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("email")
    val email: String
)