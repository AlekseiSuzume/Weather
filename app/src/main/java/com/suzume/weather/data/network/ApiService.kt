package com.suzume.weather.data.network

import com.suzume.weather.BuildConfig
import com.suzume.weather.data.network.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    private companion object {

        const val QUERY_PARAMS_API_KEY = "appid"
        const val QUERY_PARAMS_LANG = "lang"
        const val QUERY_PARAMS_UNITS = "units"
        const val QUERY_PARAMS_CITY = "q"

        const val API_KEY = BuildConfig.API_KEY
        const val LANG_DEFAULT = "ru"
        const val UNITS_DEFAULT = "metric"
        const val CITY_DEFAULT = "Novosibirsk"
    }

    @GET("weather")
    suspend fun getWeather(
        @Query(QUERY_PARAMS_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAMS_LANG) lang: String = LANG_DEFAULT,
        @Query(QUERY_PARAMS_UNITS) unit: String = UNITS_DEFAULT,
        @Query(QUERY_PARAMS_CITY) city: String = CITY_DEFAULT,
    ): WeatherDto
}