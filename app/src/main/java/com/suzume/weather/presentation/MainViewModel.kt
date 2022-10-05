package com.suzume.weather.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suzume.weather.R
import com.suzume.weather.domain.usecases.GetWeatherUseCase
import com.suzume.weather.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val communication: Communication,
    private val manageResources: ManageResources,
) : ViewModel() {

    fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        communication.observe(owner, observer)
    }

    fun loadWeather(city: String) {
        communication.updateData(Progress)
        val loadWeatherJob = viewModelScope.launch {
            try {
                loadDataUseCase(city)
            } catch (exception: Exception) {
                when (exception) {
                    is UnknownHostException -> communication.updateData(
                        Error(manageResources.string(R.string.error_connection))
                    )
                    is HttpException -> communication.updateData(
                        Error(manageResources.string(R.string.error_unknown_city))
                    )
                }
            }
        }
        viewModelScope.launch {
            loadWeatherJob.join()
            try {
                communication.updateData(ResultValue(getWeatherUseCase()))
            } catch (e: Exception) {
                //TODO
            }
        }
    }

}