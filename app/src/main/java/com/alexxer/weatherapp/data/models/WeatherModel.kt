package com.alexxer.weatherapp.data.models

import io.reactivex.Single

interface WeatherModel {
    fun getCurrentWeather(lat:Double, lon:Double):Single<Weather>

    fun getForecastWeather(lat:Double, lon:Double):Single<List<Forecast>>
}