package com.suzume.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)