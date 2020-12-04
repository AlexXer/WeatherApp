package com.alexxer.weatherapp.screen.today

import android.content.Context
import com.alexxer.weatherapp.App
import com.alexxer.weatherapp.R
import com.alexxer.weatherapp.data.model.Weather

class TodayWeatherPresenter(context: Context, private val todayWeatherView: TodayWeatherView) {
    private var currentWeather: Weather? = null
    private val coolWeatherModel = (context.applicationContext as App).coolWeatherModel

    private fun onWeatherUpdate(weather: Weather?) {
        currentWeather = weather
        todayWeatherView.updateTemperatureAndWeatherTextView(formatTemperatureAndWeather(currentWeather))
        todayWeatherView.updateWeatherImageView(getIcon(weather?.iconId))
        todayWeatherView.updateHumidityTextView(formatHumidity(currentWeather))
        todayWeatherView.updatePressureTextView(formatPressure(currentWeather))
        todayWeatherView.updateWindDirectionsTextView(convertWindDirection(currentWeather))
        todayWeatherView.updateWindSpeedTextView(formatWindSpeed(currentWeather))
        todayWeatherView.updateLocationTextView(formatLocation(currentWeather))
        todayWeatherView.updatePrecipitationTextView(formatPrecipitation(currentWeather))
    }

    private fun formatLocation(weather: Weather?): String {
        return "${weather?.town ?: "No data"}, ${weather?.country ?: "No data"}"
    }

    fun requestShare(): String {
        return "Today weather in ${currentWeather?.town}, ${currentWeather?.country} " +
                "Temperature is ${currentWeather?.temperature?.toInt()} °C." +
                "Pressure is ${formatPressure(currentWeather)}" +
                "Wind: speed - ${formatWindSpeed(currentWeather)}, direction - ${convertWindDirection(currentWeather)}. \n " +
                "Have a nice day :)"
    }

    private fun formatTemperatureAndWeather(weather: Weather?): String {
        return "${weather?.temperature?.toInt() ?: "No data"} °C | ${weather?.description ?: "No data"}"
    }

    private fun formatHumidity(weather: Weather?): String {
        return "${weather?.humidity?.toInt() ?: "No data"} %"
    }

    private fun formatPressure(weather: Weather?): String {
        return "${weather?.pressure?.toInt() ?: "No data"} hPa"
    }

    private fun formatWindSpeed(weather: Weather?): String {
        return "${weather?.windSpeed?.toInt() ?: "No data"} km/h"
    }

    private fun convertWindDirection(weather: Weather?): String {
        return if (weather?.windDirection != null) {
            when (weather.windDirection) {
                in 0.0..45.0 -> "N"
                in 45.0..90.0 -> "NE"
                in 90.0..135.0 -> "E"
                in 135.0..180.0 -> "SE"
                in 180.0..225.0 -> "S"
                in 225.0..270.0 -> "SW"
                in 270.0..315.0 -> "W"
                in 315.0..360.0 -> "NW"
                else -> "No data"
            }
        } else {
            "No data"
        }
    }

    private fun formatPrecipitation(weather: Weather?): String {
        return if (weather?.precipitation != null) {
            "${weather.precipitation} mm"
        } else {
            "No data"
        }
    }

    private fun getIcon(id: String?): Int {
        return when (id) {
            "01d" -> R.drawable.ic_sun
            "01n" -> R.drawable.ic_moon
            "02d" -> R.drawable.ic_clouds_and_sun
            "02n" -> R.drawable.ic_cloudy_night
            "03d", "03n" -> R.drawable.ic_cloudy
            "04d", "04n" -> R.drawable.ic_broken_clouds
            "09d", "09n" -> R.drawable.ic_heavy_rain
            "10d" -> R.drawable.ic_day_rain
            "10n" -> R.drawable.ic_night_rain
            "11d", "11n" -> R.drawable.ic_thunderstorm
            "12d", "12n" -> R.drawable.ic_snowflake
            "50d", "50n" -> R.drawable.ic_mist
            else -> R.mipmap.launcher_icon
        }
    }

    fun onStart() {
        coolWeatherModel
            .getWeatherUpdate()
            .subscribe({ onWeatherUpdate(it) }) { it.printStackTrace() }
    }
}