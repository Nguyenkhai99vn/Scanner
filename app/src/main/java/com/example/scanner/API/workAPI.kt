package com.example.scanner.API


import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface workAPI {
    // Link : http://doczy.net/scantoword/Services.asmx/OCRToWord
//    @Headers("Content-Type : multipart/form-data")
    @Multipart
    @POST("scantoword/Services.asmx/OCRToWord")
    fun uploadImageToWord (@Part file: List<MultipartBody.Part>) : Call<String>

    @Multipart
    @POST("scantoword/Services.asmx/OCRToText")
    fun uploadImageToText (@Part file: List<MultipartBody.Part>) : Call<String>

}