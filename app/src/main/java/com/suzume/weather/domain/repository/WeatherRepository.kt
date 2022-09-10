package com.suzume.weather.domain.repository

import androidx.lifecycle.LiveData
import com.suzume.weather.domain.entity.Weather

interface WeatherRepository {

    suspend fun loadData(city: String)

    fun getWeather(): LiveData<Weather>

}