package com.suzume.weather.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDbModel(
    @PrimaryKey
    val id: Int = 1,
    val name: String,
    val lastTimeUpdate: String,
    val pressure: Int,
    val humidity: Int,
    val temp: Double,
    val description: String,
    val icon: String,
    val windSpeed: Double,
)
