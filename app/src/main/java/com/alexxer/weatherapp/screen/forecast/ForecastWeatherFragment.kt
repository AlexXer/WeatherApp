package com.alexxer.weatherapp.screen.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alexxer.weatherapp.R


class ForecastWeatherFragment : Fragment(), ForecastWeatherView {
    private lateinit var presenter: ForecastWeatherPresenter
    private lateinit var rvForecast: RecyclerView
    private lateinit var adapter: DateForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_forecast_weather, container, false)
        rvForecast = root.findViewById(R.id.forecast_rv)
        presenter = ForecastWeatherPresenter(requireContext(), this)
        adapter = DateForecastAdapter(presenter)
        rvForecast.adapter = adapter
        presenter.onStart()
        return root
    }

    override fun updateData() {
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}