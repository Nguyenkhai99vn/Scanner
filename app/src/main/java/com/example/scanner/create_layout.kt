package com.example.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import com.example.scanner.API.workAPI
import com.example.scanner.API.workService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_layout.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class create_layout : AppCompatActivity() {

    //    val path = CameraActivity().getOutputDirectory()
    //val filepath = "[$path]2021-04-17-10-22-26-829.jpg"
    //val filepath = "C:/Users/nguye/AndroidStudioProjects/Scanner/app/src/main/res/drawable/test.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_layout)
        val intent: Intent = getIntent();
        val isSelect = intent.getIntExtra("convert", 0 );
        btn_next.setOnClickListener {
            val intent: Intent = Intent(this, AddImage::class.java)
            intent.putExtra("convert" , isSelect)
            startActivity(intent)
            finish()
        }
    }



}
