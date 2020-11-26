package com.alexxer.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexxer.weatherapp.data.models.CachedWeatherModel
import com.alexxer.weatherapp.data.models.ComplexWeatherModel
import com.alexxer.weatherapp.data.models.RemoteWeatherModel


class ForecastWeatherFragment : Fragment() {

    private lateinit var complex: ComplexWeatherModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        complex = ComplexWeatherModel(CachedWeatherModel(requireContext()), RemoteWeatherModel())
        complex
            .getForecastWeather(37.421998333333335, -122.08400000000002)
            .subscribe()
        return inflater.inflate(R.layout.fragment_forecast_weather, container, false)
    }

}