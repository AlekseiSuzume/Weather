package com.suzume.weather.domain.usecases

import com.suzume.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {

    suspend operator fun invoke(city: String) = repository.loadData(city)

}