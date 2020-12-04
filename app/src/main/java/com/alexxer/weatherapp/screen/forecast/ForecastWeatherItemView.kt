package com.alexxer.weatherapp.screen.forecast

interface ForecastWeatherItemView {
    fun updateTemperature(temp: String)
    fun updateTime(time: String)
    fun updateWeatherDescription(description: String)
    fun updateImageView(iconId: Int)
}