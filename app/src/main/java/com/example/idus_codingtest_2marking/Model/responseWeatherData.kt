package com.example.idus_codingtest_2marking.Model

class responseWeatherData(
    var local: String, var todayWeatherStateName: String, var todayWeatherStatAbbr: String, var todayWeatherTheTemp: Int
    , var todayWeatherHumidity: Int, var tomorrowWeatherStateName:String, var tomorrowWeatherStateAbbr: String, var tomorrowWeatherTheTemp: Int, var tomorrowWeatherHumidity: Int) {
    init {
        this.local = local
        this.todayWeatherStateName = todayWeatherStateName
        this.todayWeatherStatAbbr = todayWeatherStatAbbr
        this.todayWeatherTheTemp  = todayWeatherTheTemp
        this.todayWeatherHumidity = todayWeatherHumidity
        this.tomorrowWeatherStateName = tomorrowWeatherStateName
        this.tomorrowWeatherStateAbbr = tomorrowWeatherStateAbbr
        this.tomorrowWeatherTheTemp = tomorrowWeatherTheTemp
        this.tomorrowWeatherHumidity = tomorrowWeatherHumidity
    }
}
