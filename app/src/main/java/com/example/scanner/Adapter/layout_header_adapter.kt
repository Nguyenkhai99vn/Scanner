package com.example.scanner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IClickItemImageListener
import com.example.scanner.Interface.IclickItemImageLayout
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.fragment_header_1.view.*
import kotlinx.android.synthetic.main.fragment_header_2.view.*
import java.util.zip.Inflater

private const val TYPE_HEADER1 : Int = 1
private const val TYPE_HEADER2 : Int = 2

class layout_header_adapter(private val list: List<Layout> ,private val num : Int , val iclickItemImageLayout: IclickItemImageLayout ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class headerViewHolder(itemView : View , val iclickItemImageLayout: IclickItemImageLayout ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(layout: Layout, num: Int) {

            when (num) {
                1 -> {
                    val imgViewHeader : ImageView = itemView.img_header1
                    imgViewHeader.setImageURI(layout.header?.imageURL?.toUri())
                    imgViewHeader.setOnClickListener {
                        iclickItemImageLayout.clickImage(layout)
                    }
                }
                2 -> {
//                val url = "/storage/emulated/0/Android/media/com.example.scanner/Scanner/02_10_20.jpg"
                    val imgViewHeader : ImageView = itemView.img_header2
                    imgViewHeader.setImageURI(layout.header?.imageURL?.toUri())
                    imgViewHeader.setOnClickListener {
                        iclickItemImageLayout.clickImage(layout)
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (num == TYPE_HEADER1)
        {
            TYPE_HEADER1
        }else{
            TYPE_HEADER2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER2){
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_header_2, parent , false)
            return headerViewHolder(inflate , iclickItemImageLayout)
        }else{
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_header_1, parent , false)
            return headerViewHolder(inflate , iclickItemImageLayout)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as headerViewHolder).bind(list[position], num)
    }

    override fun getItemCount(): Int = list.size
}