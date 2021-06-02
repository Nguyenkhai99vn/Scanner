package com.example.scanner.API

import com.example.scanner.Model.ModelToPDF.FetchLayout
import com.example.scanner.Model.ModelToPDF.Layout
import retrofit2.http.GET
import retrofit2.http.Query

interface cellAPI {

    @GET("/api/Cell")
    fun getCell (@Query("layoutID") layoutID : Int) : retrofit2.Call<List<FetchLayout>>
}