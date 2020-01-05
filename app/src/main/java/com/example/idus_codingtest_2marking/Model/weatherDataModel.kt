package com.example.idus_codingtest_2marking.Model

import com.google.gson.annotations.SerializedName

data class weatherDataModel(
    val title: String = "",

    @SerializedName("consolidated_weather")
    val weatherList: List<ConsolidateDataModel> = mutableListOf()
){
    data class ConsolidateDataModel(
        @SerializedName("weather_state_name")
        val weatherStateName: String = "",

        @SerializedName("weather_state_abbr")
        val weatherStateAddr: String = "",

        @SerializedName("the_temp")
        val theTemp: Double = 0.0,

        @SerializedName("humidity")
        val humidity: Int = 0
    )
}