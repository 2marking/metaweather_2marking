package com.example.suhey.pbadmin_android.Login.Main.Retrofit

import com.google.gson.annotations.SerializedName


data class responseLocalData (
    @SerializedName("title")
    val title: String = "",

    @SerializedName("location_type")
    val locationType: String = "",

    @SerializedName("woeid")
    val woeId: Int = 0,

    @SerializedName("latt_long")
    val lattLong: String = ""
)