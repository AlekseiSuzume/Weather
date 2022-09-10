package com.suzume.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp")
    val temp: Double,
)