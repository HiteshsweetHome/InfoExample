package com.example.infoexample.model


import com.google.gson.annotations.SerializedName

data class AboutModel(
    @SerializedName("rows")
    val rows: List<FactsModel>,
    @SerializedName("title")
    val title: String
)