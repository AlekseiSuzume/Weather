package com.suzume.weather.di

import android.app.Application
import com.suzume.weather.data.database.AppDatabase
import com.suzume.weather.data.database.WeatherDao
import com.suzume.weather.data.repository.WeatherRepositoryImpl
import com.suzume.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @Provides
        fun provideWeatherDao(application: Application): WeatherDao {
            return AppDatabase.getInstance(application).weatherDao()
        }
    }

}