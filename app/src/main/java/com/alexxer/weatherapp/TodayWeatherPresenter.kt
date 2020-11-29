package com.alexxer.weatherapp

import android.content.Context
import com.alexxer.weatherapp.data.models.TodayWeatherView
import com.alexxer.weatherapp.data.models.Weather

class TodayWeatherPresenter(context: Context, private val todayWeatherView: TodayWeatherView) {
    private var currentWeather: Weather? = null

    init {
        val coolWeatherModel = CoolWeatherModel(context)

        coolWeatherModel
            .getWeatherUpdate()
            .subscribe({ onWeatherUpdate(it) }) { it.printStackTrace() }
        coolWeatherModel.setLocation(GeoLocation(0.1, 0.1))
    }

    private fun onWeatherUpdate(weather: Weather?) {
        currentWeather = weather
        todayWeatherView.updateTemperatureAndWeatherTextView(formatTemperatureAndWeather(currentWeather))
        todayWeatherView.updateWeatherImageView(R.drawable.ic_wind_dir_24)
        todayWeatherView.updateHumidityTextView(formatHumidity(currentWeather))
        todayWeatherView.updatePressureTextView(formatPressure(currentWeather))
        todayWeatherView.updateWindDirectionsTextView(convertWindSpeed(currentWeather))
        todayWeatherView.updateWindSpeedTextView(formatWindSpeed(currentWeather))
        todayWeatherView.updateLocationTextView(formatLocation(currentWeather))
        todayWeatherView.updatePrecipitationTextView(formatPrecipitation(currentWeather))

    }

    private fun formatLocation(weather: Weather?): String {
        return "${weather?.town ?: "No data"}, ${weather?.country ?: "No data"}"
    }

    fun requestShare(): String {

        return currentWeather.toString()
        //Внутри метод  красивого вывода погоды
        //провеерка на анал

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

    private fun convertWindSpeed(weather: Weather?): String {
        return when (weather?.windDirection!!) {
            in 0.0..45.0 -> "N"
            in 45.0..90.0 -> "NE"
            in 90.0..135.0 -> "E"
            in 135.0..180.0 -> "SE"
            in 180.0..225.0 -> "S"
            in 225.0..270.0 -> "SW"
            in 270.0..315.0 -> "W"
            in 315.0..360.0 -> "NW"
            else ->  "No data"
        }
    }


    fun formatPrecipitation(weather: Weather?):String{
        return if (weather?.precipitation != null){
            "${weather?.precipitation} mm"
        }else{
            "No data"
        }
    }


//    private fun selectWeatherImage(iconId:String): Int{
//        when(currentWeather?.iconId){
//
//        }
//    }


}