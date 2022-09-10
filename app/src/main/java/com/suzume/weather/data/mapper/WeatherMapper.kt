package com.suzume.weather.data.mapper

import com.suzume.weather.data.database.WeatherDbModel
import com.suzume.weather.data.network.model.WeatherDto
import com.suzume.weather.domain.entity.Weather
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: WeatherDto): WeatherDbModel {
        return WeatherDbModel(
            name = dto.name,
            lastTimeUpdate = getCurrentTime(),
            pressure = (dto.main.pressure * 0.75).roundToInt(),
            humidity = dto.main.humidity,
            temp = (dto.main.temp * 10.0).roundToInt() / 10.0,
            description = dto.description[0].description,
            icon = dto.description[0].icon,
            windSpeed = (dto.wind.speed * 10.0).roundToInt() / 10.0
        )
    }

    fun mapDbModelToEntity(dbModel: WeatherDbModel): Weather {
        return Weather(
            name = dbModel.name,
            lastTimeUpdate = dbModel.lastTimeUpdate,
            pressure = dbModel.pressure,
            humidity = dbModel.humidity,
            temp = dbModel.temp,
            description = dbModel.description,
            icon = dbModel.icon,
            windSpeed = dbModel.windSpeed
        )
    }

    private fun getCurrentTime(): String {
        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}