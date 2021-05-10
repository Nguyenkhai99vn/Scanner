package com.example.scanner.API

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class workService {

    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create()
//    private val client = OkHttpClient.Builder().build()
    private val client = OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    // Link : http://doczy.net/live/Services.asmx/ConvertToWord
    fun retrofit(): Retrofit {
    val retrofit =   Retrofit.Builder()
            .baseUrl("http://doczy.net/")
            .client(client.build())
            .addConverterFactory(ToStringConverterFactory())
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

}

