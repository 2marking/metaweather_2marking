package com.example.idus_codingtest_2marking

import com.example.idus_codingtest_2marking.Model.responseLocalData
import com.example.idus_codingtest_2marking.Model.weatherDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("/api/location/search/")
    fun requestLocationSearch(@Query("query") query: String) : Call<List<responseLocalData>>

    @GET("/api/location/{woeid}")
    fun requestLocation(@Path("woeid") woeId : Int) : Call<weatherDataModel>
}