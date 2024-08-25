package com.example.weatherappassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappassignment.data.WeatherApiResponse
import com.example.weatherappassignment.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val _weatherData = MutableLiveData<NetworkResponse<WeatherApiResponse>>()
    val weatherData: LiveData<NetworkResponse<WeatherApiResponse>> get() = _weatherData

    fun getData(latitude: Double, longitude: Double) {
        _weatherData.value = NetworkResponse.Loading

        viewModelScope.launch {
            _weatherData.value = repository.getWeatherData(latitude, longitude)
        }
    }
}