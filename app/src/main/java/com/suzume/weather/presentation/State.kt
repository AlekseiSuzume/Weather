package com.suzume.weather.presentation

import com.suzume.weather.domain.entity.Weather

sealed class State

object Progress : State()
class Error(val message: String) : State()
class ResultValue(val weather: Weather?) : State()