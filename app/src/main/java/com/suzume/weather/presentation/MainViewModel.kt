package com.suzume.weather.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suzume.weather.domain.usecases.GetWeatherUseCase
import com.suzume.weather.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class MainViewModel @Inject constructor (
    private val loadDataUseCase: LoadDataUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _errorConnection = MutableLiveData<Boolean>()
    val errorConnection: LiveData<Boolean> = _errorConnection

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorUnknownCity = MutableLiveData<Boolean>()
    val errorUnknownCity: LiveData<Boolean> = _errorUnknownCity

    fun getWeather() = getWeatherUseCase()

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                loadDataUseCase(city)
            } catch (e: Exception) {
                Log.d("MyTest:MainViewModel", e.toString())
                when (e) {
                    is UnknownHostException -> _errorConnection.value = true
                    is HttpException -> _errorUnknownCity.value = true
                }
            }
            _isLoading.value = false
        }
    }
}