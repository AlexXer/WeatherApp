package com.alexxer.weatherapp.screen.today

interface TodayWeatherView {
    fun updateTemperatureAndWeatherTextView(temperatureAndWeather: String)
    fun updateWeatherImageView(drawableId: Int)
    fun updatePressureTextView(pressure: String)
    fun updateHumidityTextView(humidity: String)
    fun updateWindSpeedTextView(windSpeed: String)
    fun updateWindDirectionsTextView(windDirection: String)
    fun updateLocationTextView(location: String)
    fun updatePrecipitationTextView(precipitation: String)
}