package com.example.weatherappassignment.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.weatherappassignment.Constnats

object RetrofitInstance {

    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constnats.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}