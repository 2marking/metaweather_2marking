package com.example.idus_codingtest_2marking

import android.os.AsyncTask
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitTask(private val executionHandler: RetrofitExecutionHandler, private val targetURL: String) {
    private var asyncTaskManager: AsyncTaskManager? = null

    fun execute(vararg request: RetrofitRequestParam) {
        if (request == null) return

        asyncTaskManager = AsyncTaskManager()
        asyncTaskManager!!.execute(*request)
    }

    internal inner class AsyncTaskManager : AsyncTask<RetrofitRequestParam, Any, RetrofitResponseParam>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg request: RetrofitRequestParam): RetrofitResponseParam {
            val retrofit = Retrofit.Builder()
                .baseUrl(targetURL).addConverterFactory(GsonConverterFactory.create())
                .build()

            val response = executionHandler.onBeforeAyncExcute(retrofit, request[0])
            val bError = request[0].isError
            request[0].isError = false

            if (response==null){
                return RetrofitResponseParam(request[0].taskNum, null, bError)
            } else {
                return RetrofitResponseParam(request[0].taskNum, response!!, bError)
            }
        }

        override fun onPostExecute(response: RetrofitResponseParam) {
            executionHandler.onAfterAyncExcute(response)
        }
    }

    interface RetrofitExecutionHandler {
        fun onBeforeAyncExcute(retrofit: Retrofit, paramRequest: RetrofitRequestParam): Any?
        fun onAfterAyncExcute(response: RetrofitResponseParam)
    }

    class RetrofitRequestParam(var taskNum: Int, var paramRequest: Any?) {
        var isError: Boolean = false
        init {
            isError = false
        }
    }

    class RetrofitResponseParam(val taskNum: Int, val response: Any?, var isError: Boolean)

    class RetrofitTaskError(val taskNum: Int, val msg: String)
}
