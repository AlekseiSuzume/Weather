package com.suzume.weather.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.suzume.weather.data.database.AppDatabase
import com.suzume.weather.data.database.WeatherDao
import com.suzume.weather.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    companion object {

        @Provides
        fun provideWeatherDao(application: Application): WeatherDao {
            return AppDatabase.getInstance(application).weatherDao()
        }
    }

}