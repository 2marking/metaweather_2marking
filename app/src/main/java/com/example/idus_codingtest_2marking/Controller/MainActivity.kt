package com.example.idus_codingtest_2marking.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.idus_codingtest_2marking.Model.*
import com.example.idus_codingtest_2marking.R
import com.example.idus_codingtest_2marking.RetrofitTask
import com.example.idus_codingtest_2marking.WeatherService

class MainActivity : AppCompatActivity(), RetrofitTask.RetrofitExecutionHandler, SwipeRefreshLayout.OnRefreshListener {
    private var RETROFIT_MODE = 0x01
    private var RETROFIT_FIRST_MODE = 0x01
    private var RETROFIT_REFRESH_MODE = 0x02
    private var RETROFIT_META_LOCALINFO = 0x00
    private var RETROFIT_META_WEATHERINFO = 0x01

    lateinit var retrofit: RetrofitTask
    lateinit var weatherListAdapter : WeatherListAdapter
    private var weatherDataList: MutableList<responseWeatherData> = ArrayList()
    private var isProgress: Boolean = false
    private var regionDataSize: Int=0

    private fun metaWeatherRetrofit(){
        weatherDataList.clear()
        weatherListAdapter = WeatherListAdapter(weatherDataList)
        rv_weatherInfo.adapter = weatherListAdapter
        rv_weatherInfo.layoutManager = LinearLayoutManager(applicationContext)

        retrofit = RetrofitTask(this, resources.getString(R.string.METAWEATHER_TARGET_URL))
        val requestLotInfoChange = requestLocalData(resources.getString(R.string.SEARCH_LOCAL_WORD))
        val requestParam = RetrofitTask.RetrofitRequestParam(0, requestLotInfoChange)
        retrofit.execute(requestParam)
    }

    override fun onBeforeAyncExcute(retrofit: Retrofit, paramRequest: RetrofitTask.RetrofitRequestParam): Any? {
        var response: Any? = null
        val taskNum = paramRequest.taskNum
        val requestParam = paramRequest.paramRequest
        val mainRetrofit = retrofit.create(WeatherService::class.java)

        if (this == null) return null

        when (RETROFIT_MODE){
            RETROFIT_FIRST_MODE -> {
                isProgress = false
                when (isProgress) {
                    false -> progressBar.show()
                }
            }
        }

        try {
            when (taskNum) {
                RETROFIT_META_LOCALINFO -> {
                    val retrofitCall = mainRetrofit.requestLocationSearch(resources.getString(R.string.SEARCH_LOCAL_WORD))
                    val Data = retrofitCall.execute()
                    response = Data.body()
                }
                RETROFIT_META_WEATHERINFO -> {
                    val retrofitCall = mainRetrofit.requestLocation(requestParam.toString().toInt())
                    val Data = retrofitCall.execute()
                    response = Data.body()
                }
            }
        } catch (ex: UnknownHostException) {
            paramRequest.isError = true
            response = (resources.getString(R.string.errmsg_retrofit_unknown)).toString()
        } catch (ex: ConnectException) {
            paramRequest.isError = true
            response = (resources.getString(R.string.errmsg_retrofit_servernetwork)).toString()
        } catch (ex: Exception) {
            paramRequest.isError = true
            response = (resources.getString(R.string.errmsg_retrofit_cs_unknown)).toString()
        }
        return response
    }

    override fun onAfterAyncExcute(response: RetrofitTask.RetrofitResponseParam) {
        val taskNum = response.taskNum
        val responseData = response.response

        if (response.response == null) return
        else if (response.isError) {
            val errMsg = response.response as String
            Toast.makeText(this.baseContext, errMsg, Toast.LENGTH_SHORT).show()
            return
        }
        when (taskNum) {
            RETROFIT_META_LOCALINFO -> {
                retrofit = RetrofitTask(this, resources.getString(R.string.METAWEATHER_TARGET_URL))
                val resData = responseData as List<responseLocalData>

                regionDataSize = resData.size
                for (i in resData){
                    val requestLotInfoChange = requestWeatherData(i.woeId)
                    val requestParam = RetrofitTask.RetrofitRequestParam(RETROFIT_META_WEATHERINFO, requestLotInfoChange.woeid)
                    retrofit.execute(requestParam)
                }
            }
            RETROFIT_META_WEATHERINFO -> {
                val resData = responseData as weatherDataModel
                var localWeather = responseWeatherData(resData.title,
                    resData.weatherList[0].weatherStateName,
                    resData.weatherList[0].weatherStateAddr,
                    resData.weatherList[0].theTemp.toInt(),
                    resData.weatherList[0].humidity,
                    resData.weatherList[1].weatherStateName,
                    resData.weatherList[1].weatherStateAddr,
                    resData.weatherList[1].theTemp.toInt(),
                    resData.weatherList[1].humidity)

                when (weatherDataList.size){
                    0 -> {
                        val rvHeader = responseWeatherData("header","","",0,0,"","",0,0)
                        weatherDataList.add(rvHeader)
                    }
                }

                weatherDataList.add(localWeather)
                if (weatherDataList.size-1==regionDataSize) {
                    when (RETROFIT_MODE){
                        RETROFIT_FIRST_MODE -> {
                            isProgress=true
                            when (isProgress) {
                                true -> progressBar.hide()
                            }
                        }
                        RETROFIT_REFRESH_MODE -> {
                            layout_swipe.isRefreshing = false
                        }
                    }
                    weatherListAdapter = WeatherListAdapter(weatherDataList)
                    rv_weatherInfo.adapter = weatherListAdapter
                    rv_weatherInfo.layoutManager = LinearLayoutManager(applicationContext)
                    weatherListAdapter.notifyDataSetChanged()
                }
            }
        }

    }

    override fun onRefresh() {
        RETROFIT_MODE = RETROFIT_REFRESH_MODE
        rv_weatherInfo.removeAllViews()
        metaWeatherRetrofit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 최초 통신 시작
        metaWeatherRetrofit()
        // SwipeLayout 리스너 설정
        layout_swipe.setOnRefreshListener(this)
    }
}
