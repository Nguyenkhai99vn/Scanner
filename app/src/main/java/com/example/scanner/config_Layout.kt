package com.example.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import kotlinx.android.synthetic.main.activity_config_layout.*

class config_Layout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_layout)
        val intent = Intent(this, edit_Layout::class.java)
        getdata(intent)
        getHeader(intent)
        getFooter(intent)
        btn_next.setOnClickListener {
            startActivity(intent);
        }
    }

    fun getdata(intent: Intent){
        val intent1 : Intent = getIntent()
        val num = intent1.getIntExtra("numcontent" , 0)
        val uri = intent1.getStringExtra("uri")
        intent.putExtra("numcontent" ,num)
        intent.putExtra("uri" , uri)

    }

    fun getHeader(intent: Intent){
        var header : Int
        rltl5.setOnClickListener {
            img_noneheader.visibility = View.VISIBLE
            img_logoheader.visibility = View.GONE
            img_textheader.visibility = View.GONE
            header = 0
            intent.putExtra("header" , header)
        }
        rltl6.setOnClickListener {
            img_noneheader.visibility = View.GONE
            img_logoheader.visibility = View.VISIBLE
            img_textheader.visibility = View.GONE
            header = 1
            intent.putExtra("header" , header)
        }
        rltl7.setOnClickListener {
            img_noneheader.visibility = View.GONE
            img_logoheader.visibility = View.GONE
            img_textheader.visibility = View.VISIBLE
            header = 2
            intent.putExtra("header" , header)
        }
    }
    fun getFooter(intent: Intent){
        var footer : Int
        rltl8.setOnClickListener {
            img_nonefooter.visibility = View.VISIBLE
            img_numfooter.visibility = View.GONE
            img_textfooter.visibility = View.GONE
            footer = 0
            intent.putExtra("footer" , footer)
        }
        rltl9.setOnClickListener {
            img_nonefooter.visibility = View.GONE
            img_numfooter.visibility = View.VISIBLE
            img_textfooter.visibility = View.GONE
            footer = 1
            intent.putExtra("footer" , footer)
        }
        rltl10.setOnClickListener {
            img_nonefooter.visibility = View.GONE
            img_numfooter.visibility = View.GONE
            img_textfooter.visibility = View.VISIBLE
            footer = 2
            intent.putExtra("footer" , footer)
        }
    }
}