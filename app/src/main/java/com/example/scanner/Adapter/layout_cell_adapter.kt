package com.example.scanner.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.FragmentTransitionSupport
import com.example.scanner.*
import com.example.scanner.Interface.IClickBtnSheetListener
import com.example.scanner.Interface.IclickItemImageLayout
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.Model.all_item_layout_pdf
import com.example.scanner.Model.item_File
import com.example.scanner.Model.item_image_camera
import com.example.scanner.Model.item_sheet
import kotlinx.android.synthetic.main.dialog_menu.*
import kotlinx.android.synthetic.main.fragment_layout.view.*

class layout_cell_adapter( private val context : Context ,
                           val list : List<all_item_layout_pdf> ,
                           val numheader : Int? = null,
                           val numcontent : Int? = null,
                           val numfooter : Int? = null) :
        RecyclerView.Adapter<layout_cell_adapter.AlllayoutViewHolder>() {

    class AlllayoutViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         val header : RecyclerView = itemView.rccv_Header
         val content : RecyclerView = itemView.rccv_Content
         val footer : RecyclerView = itemView.rccv_Footer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlllayoutViewHolder {

        val View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_layout , parent , false)
        return AlllayoutViewHolder(View)
    }
    override fun onBindViewHolder(holder: AlllayoutViewHolder, position: Int) {
        setHeaderItemRecycleView(holder.header , list[position].header)
        setContentItemRecycleView(holder.content , list[position].content)
        setFooterItemRecycleView(holder.footer , list[position].footer)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun setHeaderItemRecycleView(recycleview : RecyclerView , headerlayout : List<Layout>){
        if (numheader == 0 ){
            recycleview.visibility = View.GONE
        }else{
            val headeradapter = numheader?.let { layout_header_adapter(headerlayout , num = it , object : IclickItemImageLayout {
                override fun clickImage(itemLayout: Layout) {
//                    Toast.makeText(context , "hello baby" , Toast.LENGTH_SHORT).show()
//                    openMenuSelectImgDialog(Gravity.BOTTOM)
                }
            }) }
            recycleview.layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)
            recycleview.adapter = headeradapter
        }
    }

    private fun setContentItemRecycleView(recycleview : RecyclerView , headerlayout : List<Layout>){
            val contentadapter = numcontent?.let { layout_content_adapter(headerlayout, num = it) }
            recycleview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recycleview.adapter = contentadapter
    }

    private fun setFooterItemRecycleView(recycleview : RecyclerView , headerlayout : List<Layout>){
        if (numfooter == 0 ){
            recycleview.visibility = View.GONE
        }else {
            val footeradapter = numfooter?.let { layout_footer_adapter(headerlayout, num = it) }
            recycleview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recycleview.adapter = footeradapter
        }
    }
//    fun openMenuSelectImgDialog(gravity : Int){
//        val arrsheet = ArrayList<item_image_camera> ()
//        arrsheet.add(item_image_camera(R.drawable.file_convert, "Open Camera"))
//        arrsheet.add(item_image_camera(R.drawable.file_create, "Select From Galery"))
//
//        val dialog  = DialogSelectImageFragment(arrsheet,object : IClickBtnSheetListener {
//            override fun clickItemSheetSelectImage(itemSheet: item_image_camera) {
//                val id = itemSheet.name
//                when (id) {
//                    "Open Camera" -> {
//                        val intent = Intent(context, CameraActivity::class.java)
//                        startActivity(context, intent, Bundle.EMPTY)
//                    }
//                    "Select From Galery" -> {
//                        val intent = Intent(context, AddImage::class.java)
//                        var isSelect : Int = 5
//                        intent.putExtra("convert", isSelect)
//                        startActivity(context, intent, Bundle.EMPTY)
//                    }
//                }
////                val header = headerlayout[position].header?.imageURL
//            }
//        })
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
////        dialog.setContentView(R.layout.item_sheet_camera_img)
//        val window : Window? = dialog.window
//        if (window == null) {
//            return
//        }else{
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT , WindowManager.LayoutParams.WRAP_CONTENT)
//            window.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
//            val windowAttributes : WindowManager.LayoutParams = window.attributes
//            windowAttributes.gravity = gravity
//            window.attributes = windowAttributes
//        }
//
//        if (Gravity.BOTTOM == gravity){
//            dialog.setCancelable(true)
//        }else{
//            dialog.setCancelable(false)
//        }
//        dialog.show(FragmentManager , dialog.tag)
//    }
//    private fun clickBottonSheetFragment(){
//
//
//        val myBottonSheetFragment = BtnSheetSelectImageFragment(arrsheet,object : IClickBtnSheetListener {
//            override fun clickItemSheetSelectImage(itemSheet: item_image_camera) {
//                val id = itemSheet.name
//                when (id) {
//                    "Open Camera" -> {
//                        val intent = Intent(context, CameraActivity::class.java)
//                        startActivity(context, intent, Bundle.EMPTY)
//                    }
//                    "Select From Galery" -> {
//                        val intent = Intent(context, AddImage::class.java)
//                        var isSelect : Int = 5
//                        intent.putExtra("convert", isSelect)
//                        startActivity(context, intent, Bundle.EMPTY)
//                    }
//                }
////                val header = headerlayout[position].header?.imageURL
//            }
//        } )
//
//    }
}