package com.suzume.weather.di

import com.suzume.weather.data.repository.WeatherRepositoryImpl
import com.suzume.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

}