package com.example.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.scanner.Adapter.layout_adapter
import com.example.scanner.Interface.IClickItemLayout
import com.example.scanner.Model.item_layout
import kotlinx.android.synthetic.main.activity_create_layout.*
import kotlinx.android.synthetic.main.activity_create_layout.rccv_1
import kotlinx.android.synthetic.main.activity_create_layout.rccv_2
import kotlinx.android.synthetic.main.activity_create_layout.rccv_3
import kotlinx.android.synthetic.main.fragment_list_layout.*
import kotlin.math.log

class create_layout : AppCompatActivity() {
    lateinit var numlayout : String
    var numlay : Int = 0
    //    val path = CameraActivity().getOutputDirectory()
    //val filepath = "[$path]2021-04-17-10-22-26-829.jpg"
    //val filepath = "C:/Users/nguye/AndroidStudioProjects/Scanner/app/src/main/res/drawable/test.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_layout)
        val intent: Intent = getIntent()
//        val isSelect = intent.getIntExtra("convert", 0 )
        val uri = intent.getStringExtra("uri")
        viewListLayout()
//        val Cell = intent.getCharSequenceArrayExtra("Cell" )
        btn_next.setOnClickListener {

            val intent: Intent = Intent(this, config_Layout::class.java)
            intent.putExtra("uri" , uri)
            intent.putExtra("numcontent" , numlay)
            Log.e("num",numlay.toString())
            startActivity(intent)
            finish()


        }
    }
    private fun viewListLayout(){
        val arrlayout1 = ArrayList<item_layout>()
        val arrlayout2 = ArrayList<item_layout>()
        val arrlayout3 = ArrayList<item_layout>()
        arrlayout1.add(item_layout(R.drawable.mot , R.drawable.done))
        arrlayout1.add(item_layout(R.drawable.hai , R.drawable.done))
        arrlayout1.add(item_layout(R.drawable.ba , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.bon , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.nam , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.sau , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.bay , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.tam , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.chin , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.muoi , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mmot , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mhai , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mba , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mbon , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mlam , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.msau , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mbay , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mtam , R.drawable.done))

        rccv_1.adapter = layout_adapter(arrlayout1, object : IClickItemLayout{
            override fun onclick(itemLayout: item_layout, id : Int) {
                if (itemLayout.isSelected == false){
                    numlay = id + 1
                }
            }

        })
        rccv_1.layoutManager = GridLayoutManager(this , 3)

        rccv_2.adapter = layout_adapter(arrlayout2, object : IClickItemLayout{
            override fun onclick(itemLayout: item_layout, id : Int) {
                if (itemLayout.isSelected == false){
                    numlay = id + 1
                    Log.e("numlay1",numlay.toString())
                }
            }

        })
        rccv_2.layoutManager = GridLayoutManager(this , 3)

        rccv_3.adapter = layout_adapter(arrlayout3, object : IClickItemLayout{
            override fun onclick(itemLayout: item_layout, id : Int) {
                if (itemLayout.isSelected == false){
                    numlay = id + 9
                }
            }
        })
        rccv_3.layoutManager = GridLayoutManager(this , 3)
    }


}
