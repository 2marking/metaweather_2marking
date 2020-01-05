package com.example.idus_codingtest_2marking.Model

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