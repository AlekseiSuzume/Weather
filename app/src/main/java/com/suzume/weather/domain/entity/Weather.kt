package com.suzume.weather.domain.entity

data class Weather(
    val name: String,
    val lastTimeUpdate: String,
    val pressure: Int,
    val humidity: Int,
    val temp: Double,
    val description: String,
    val icon: String,
    val windSpeed: Double,
)
