package com.example.weatherappassignment.repository

import com.example.weatherappassignment.NetworkResponse
import com.example.weatherappassignment.api.RetrofitInstance
import com.example.weatherappassignment.api.WeatherApi
import com.example.weatherappassignment.data.WeatherApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class WeatherRepository {

    private val api: WeatherApi = RetrofitInstance.api

    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResponse<WeatherApiResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWeather(latitude, longitude)
                if (response.isSuccessful) {
                    response.body()?.let {
                        NetworkResponse.Success(it)
                    } ?: NetworkResponse.Error("No data available")
                } else {
                    NetworkResponse.Error("Error: ${response.message()}")
                }
            }
            catch (e: Exception) {
                NetworkResponse.Error("Unexpected error: ${e.message}")
            }
        }
    }
}