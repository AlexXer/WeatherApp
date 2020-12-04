package com.alexxer.weatherapp.screen.today

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alexxer.weatherapp.R

class TodayWeatherFragment : Fragment(), TodayWeatherView {
    private lateinit var presenter: TodayWeatherPresenter
    private lateinit var weatherIv: ImageView
    private lateinit var temperatureAndWeatherTextView: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var windSpeedTextView: TextView
    private lateinit var windDirectionTextView: TextView
    private lateinit var precipitationTextView: TextView
    private lateinit var locationTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_today_weather, container, false)
        val shareButton = root.findViewById<Button>(R.id.button_share)
        presenter = TodayWeatherPresenter(requireContext(), this)
        temperatureAndWeatherTextView = root.findViewById(R.id.temperature_tv)
        pressureTextView = root.findViewById(R.id.pressure_tv)
        humidityTextView = root.findViewById(R.id.humidity_tv)
        windSpeedTextView = root.findViewById(R.id.wind_speed_tv)
        windDirectionTextView = root.findViewById(R.id.wind_direction_tv)
        locationTextView = root.findViewById(R.id.location_tv)
        precipitationTextView = root.findViewById(R.id.rainfall_level_tv)
        weatherIv = root.findViewById(R.id.weather_iv)
        shareButton.setOnClickListener {
            shareWeather(presenter.requestShare())
        }
        presenter.onStart()
        return root
    }

    override fun updateTemperatureAndWeatherTextView(temperatureAndWeather: String) {
        temperatureAndWeatherTextView.text = temperatureAndWeather
    }

    override fun updateWeatherImageView(drawableId: Int) {
        weatherIv.setImageResource(drawableId)
    }

    override fun updatePressureTextView(pressure: String) {
        pressureTextView.text = pressure
    }

    override fun updateHumidityTextView(humidity: String) {
        humidityTextView.text = humidity
    }

    override fun updateWindSpeedTextView(windSpeed: String) {
        windSpeedTextView.text = windSpeed
    }

    override fun updateWindDirectionsTextView(windDirection: String) {
        windDirectionTextView.text = windDirection
    }

    override fun updateLocationTextView(location: String) {
        locationTextView.text = location
    }

    override fun updatePrecipitationTextView(precipitation: String) {
        precipitationTextView.text = precipitation
    }

    private fun shareWeather(weather: String) {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, weather)
            this.type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Send weather with friends:"))
    }
}