package com.example.idus_codingtest_2marking

import com.example.suhey.pbadmin_android.Login.Main.Retrofit.responseLocalData
import com.example.suhey.pbadmin_android.Login.Main.Retrofit.weatherDataModel
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