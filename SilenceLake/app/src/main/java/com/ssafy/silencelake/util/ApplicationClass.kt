package com.ssafy.silencelake.util

import android.app.Application
import com.ssafy.smartstore.intercepter.AddCookiesInterceptor
import com.ssafy.smartstore.intercepter.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.prefs.AbstractPreferences

class ApplicationClass : Application() {

    companion object {
        const val SERVER_URL = "http://192.168.33.134:9999/"
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        lateinit var retrofit: Retrofit
        lateinit var myToken: String
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        val okHttpClient = OkHttpClient.Builder().readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS).build()
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}