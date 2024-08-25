package com.example.weatherappassignment.api

import com.example.weatherappassignment.Constnats
import com.example.weatherappassignment.data.WeatherApiResponse
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    @GET("data/2.5/onecall")
   suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = Constnats.API_KEY
    ): Response<WeatherApiResponse>
}