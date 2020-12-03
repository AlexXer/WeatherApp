package com.alexxer.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alexxer.weatherapp.data.models.ForecastWeatherView


class ForecastWeatherFragment : Fragment(), ForecastWeatherView {

    private lateinit var presenter: ForecastWeatherPresenter
    private lateinit var rvForecast: RecyclerView
    private lateinit var adapter: DateForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_forecast_weather, container, false)
        presenter = ForecastWeatherPresenter(requireContext(), this)
        rvForecast = root.findViewById(R.id.forecast_rv)
        adapter = DateForecastAdapter(presenter)
        rvForecast.adapter = adapter
        return root
    }

    override fun updateData() {
        adapter.notifyDataSetChanged()

    }


}