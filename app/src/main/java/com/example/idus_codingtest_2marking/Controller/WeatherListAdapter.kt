package com.example.idus_codingtest_2marking.Controller

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idus_codingtest_2marking.Model.responseWeatherData
import com.example.idus_codingtest_2marking.R
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherListAdapter(weatherList:MutableList<responseWeatherData>) : RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>(){
    private var weatherList: MutableList<responseWeatherData> = weatherList
    private var RETROFIT_METAWEATHER_ICON_URL = "https://www.metaweather.com/static/img/weather/png/64/"
    private var TYPE_HEADER = 0x00

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_weather_header
            else -> R.layout.item_weather
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        return when (viewType) {
            R.layout.item_weather_header -> {
                var view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_header, parent, false)
                WeatherListViewHolder(view)
            }
            else -> {
                var view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather, parent, false)
                WeatherListViewHolder(view)
            }
        }
    }

    override fun getItemCount():Int{
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        if (position!=TYPE_HEADER){
            weatherList[position].let{item->
                with(holder){
                    tvLocalName.text = item.local

                    Glide.with(holder.itemView.context).load(RETROFIT_METAWEATHER_ICON_URL+item.todayWeatherStatAbbr+".png").into(ivTodayStateAbbr)
                    tvTodayStateName.text = item.todayWeatherStateName
                    tvTodayTemp.text = item.todayWeatherTheTemp.toString() + "°C"
                    tvTodayHumidity.text = item.todayWeatherHumidity.toString() + "%"

                    Glide.with(holder.itemView.context).load(RETROFIT_METAWEATHER_ICON_URL+item.todayWeatherStatAbbr+".png").into(ivTomorrowStateAbbr)
                    tvTomorrowStateName.text = item.tomorrowWeatherStateName
                    tvTomorrowTemp.text = item.tomorrowWeatherTheTemp.toString() + "°C"
                    tvTomorrowHumidity.text = item.tomorrowWeatherHumidity.toString() + "%"
                }
            }
        }
    }

    inner class WeatherListViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvLocalName = itemView.tv_localName
        val ivTodayStateAbbr = itemView.iv_today_weather_state_abbr
        val tvTodayStateName = itemView.tv_today_weather_state_name
        val tvTodayTemp = itemView.tv_today_the_temp
        val tvTodayHumidity = itemView.tv_today_humidity
        val ivTomorrowStateAbbr = itemView.iv_tomorrow_weather_state_abbr
        val tvTomorrowStateName = itemView.tv_tomorrow_weather_state_name
        val tvTomorrowTemp = itemView.tv_tomorrow_the_temp
        val tvTomorrowHumidity = itemView.tv_tomorrow_humidity
    }
}
