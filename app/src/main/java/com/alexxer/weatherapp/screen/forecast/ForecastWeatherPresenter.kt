package com.alexxer.weatherapp.screen.forecast

import android.content.Context
import com.alexxer.weatherapp.App
import com.alexxer.weatherapp.R
import com.alexxer.weatherapp.data.model.Forecast
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ForecastWeatherPresenter(context: Context, private val forecastWeatherView: ForecastWeatherView) {
    private var forecastDateWeather: List<Any> = listOf()
    private val coolWeatherModel = (context.applicationContext as App).coolWeatherModel
    private var disposable: Disposable? = null


    private fun onForecastUpdate(forecasts: List<Forecast>) {
        forecastDateWeather = formatForecasts(forecasts)
        forecastWeatherView.updateData()
    }

    fun onBindViewAtPosition(position: Int, view: Any) {
        val rvElement = forecastDateWeather[position]
        if (rvElement is ForecastItemData && view is ForecastWeatherItemView) {
            rvElement.dateTime.let { view.updateTime(formatTime(it)) }
            rvElement.descr.let { view.updateWeatherDescription(it) }
            rvElement.temp.let { view.updateTemperature(formatTemperature(it)) }
            rvElement.iconId?.let { view.updateImageView(getIcon(it)) }
        } else if (rvElement is DayItemData && view is ForecastDayView) {
            view.updateDate(formatDay(rvElement.day))
        }
    }

    private fun formatTime(date: Date): String {
        return SimpleDateFormat("HH:mm").format(date)
    }

    private fun formatDay(day: Date): String {
        return if (SimpleDateFormat("yyyy-MM-dd").format(day) == LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            "Today" else SimpleDateFormat("EEEE", Locale.ENGLISH).format(day)
    }

    fun getForecastsCount(): Int {
        return forecastDateWeather.size
    }

    private fun formatForecasts(forecasts: List<Forecast>): List<Any> {
        val formatter1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val res = ArrayList<Any>()
        forecasts
            .map { ForecastItemData(formatter1.parse(it.dateTime), it.temperature!!, it.description!!, it.iconId) }
            .groupBy {
                val cal = Calendar.getInstance()
                cal.time = Date(it.dateTime.time)
                cal[Calendar.HOUR_OF_DAY] = 0
                cal[Calendar.MINUTE] = 0
                cal[Calendar.SECOND] = 0
                cal[Calendar.MILLISECOND] = 0
                return@groupBy cal.time
            }
            .mapKeys { DayItemData(it.key) }
            .toList()
            .sortedBy { it.first.day.time }
            .forEach {
                res.add(it.first)
                res.addAll(it.second)
            }
        return res
    }

    private fun formatTemperature(temp: Double): String {
        return "${temp.toInt()} °C"
    }

    fun getTypeByPos(int: Int): Int {
        return if (forecastDateWeather[int] is DayItemData) 0 else 1
    }

    private fun getIcon(id: String): Int {
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
            "13d", "13n" -> R.drawable.ic_snowflake
            "50d", "50n" -> R.drawable.ic_mist
            else -> 0
        }
    }

    fun onStart() {
        disposable = coolWeatherModel.getForecastUpdate().subscribe({ onForecastUpdate(it) }) { it.printStackTrace() }
    }

    fun onDestroy() {
        disposable?.dispose()
    }

    data class DayItemData(val day: Date)

    data class ForecastItemData(val dateTime: Date, val temp: Double, val descr: String, val iconId: String?)
}