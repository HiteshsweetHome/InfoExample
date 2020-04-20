package com.example.infoexample.model


import com.google.gson.annotations.SerializedName

data class FactsModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("imageHref")
    val imageHref: String,
    @SerializedName("title")
    val title: String
)