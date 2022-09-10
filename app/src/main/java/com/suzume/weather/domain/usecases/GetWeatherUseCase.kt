package com.suzume.weather.domain.usecases

import com.suzume.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {

    operator fun invoke() = weatherRepository.getWeather()


}