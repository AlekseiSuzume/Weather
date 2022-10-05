package com.suzume.weather.data.repository

import com.suzume.weather.data.database.WeatherDao
import com.suzume.weather.data.mapper.WeatherMapper
import com.suzume.weather.data.network.ApiFactory
import com.suzume.weather.domain.entity.Weather
import com.suzume.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val mapper: WeatherMapper,
    private val dao: WeatherDao,
) : WeatherRepository {

    override suspend fun loadData(city: String) {
        val weather = ApiFactory.getApiService().getWeather(city = city)
        dao.insertWeather(mapper.mapDtoToDbModel(weather))
    }

    override suspend fun getWeather(): Weather {
        return mapper.mapDbModelToEntity(dao.getWeather())
    }

}