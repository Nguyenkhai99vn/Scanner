package com.example.scanner.API

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class cellService {
//    https://60af6fdb5b8c300017decb71.mockapi.io/api/Cell?layoutID=1
    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create()
    fun retrofit() : Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://60af6fdb5b8c300017decb71.mockapi.io")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit
    }
}