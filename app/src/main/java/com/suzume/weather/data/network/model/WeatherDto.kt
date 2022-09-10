package com.suzume.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("weather")
    val description: List<Description>,
    @SerializedName("wind")
    val wind: Wind
)