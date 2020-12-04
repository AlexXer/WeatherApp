package com.alexxer.weatherapp.screen.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexxer.weatherapp.R

class DateForecastAdapter(private val presenter: ForecastWeatherPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ForecastWeatherItemView {
        override fun updateTemperature(temp: String) {
            itemView.findViewById<TextView>(R.id.forecast_temperature_tv).text = temp
        }

        override fun updateTime(time: String) {
            itemView.findViewById<TextView>(R.id.forecast_time_tv).text = time
        }

        override fun updateWeatherDescription(description: String) {
            itemView.findViewById<TextView>(R.id.forecast_weather_tv).text =description
        }

        override fun updateImageView(iconId: Int) {
            itemView.findViewById<ImageView>(R.id.forecast_weather_iv).setImageResource(iconId)
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ForecastDayView {
        override fun updateDate(date: String) {
            itemView.findViewById<TextView>(R.id.forecast_day_tv).text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            DateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_forecast_weather_item, parent, false))
        else
            ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.body_forecast_weather_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        presenter.onBindViewAtPosition(position, holder) // проверка на принадлежность классов ххолдеров
    }

    override fun getItemCount(): Int {
        return presenter.getForecastsCount()
    }

    override fun getItemViewType(position: Int): Int {
        return presenter.getTypeByPos(position)
    }
}