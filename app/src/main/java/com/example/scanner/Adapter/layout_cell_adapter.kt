package com.example.scanner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IclickItemImageLayout
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.Model.all_item_layout_pdf
import com.example.scanner.R
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
                    Toast.makeText(context , "hello baby" , Toast.LENGTH_SHORT).show()
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
}