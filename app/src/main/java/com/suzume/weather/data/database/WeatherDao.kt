package com.suzume.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherDbModel: WeatherDbModel)

    @Query("SELECT * FROM weather")
    suspend fun getWeather(): WeatherDbModel

}