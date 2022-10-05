package com.suzume.weather.domain.repository

import com.suzume.weather.domain.entity.Weather

interface WeatherRepository {

    suspend fun loadData(city: String)

    suspend fun getWeather(): Weather

}