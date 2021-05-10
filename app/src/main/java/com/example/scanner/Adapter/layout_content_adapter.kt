package com.example.scanner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.fragment_header_1.view.*
import kotlinx.android.synthetic.main.fragment_header_2.view.*
import kotlinx.android.synthetic.main.fragment_layout_13.view.*
import kotlinx.android.synthetic.main.fragment_layout_14.view.*
import kotlinx.android.synthetic.main.fragment_layout_4.view.*
import kotlinx.android.synthetic.main.fragment_layout_5.view.*
import java.util.zip.Inflater

private const val TYPE_CONTENT4 : Int = 4
private const val TYPE_CONTENT5 : Int = 5
private const val TYPE_CONTENT13 : Int = 13
private const val TYPE_CONTENT14 : Int = 14

class layout_content_adapter(private val list: List<Layout> ,private val num : Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class contentViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {
        fun bind(layout: Layout , num: Int) {
            var count: Int? = layout.cell?.count()
            if (count != null) {
                count = count - 1
                when (num) {
                    4 -> {
                        val imgViewContent1: ImageView = itemView.img_4_1
                        val imgViewContent2: ImageView = itemView.img_4_2
                        val imgViewContent3: ImageView = itemView.img_4_3
                        for (i in 0..count) {
                            if (count == 0 ) {
                                imgViewContent1.setImageURI(count.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                            } else if (i == 1) {
                                imgViewContent1.setImageURI((count-1).let { layout?.cell?.get(it)?.imageURL?.toUri() })
                                imgViewContent2.setImageURI(count.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                            } else if (i == 2) {
                                imgViewContent1.setImageURI((count-2).let { layout?.cell?.get(it)?.imageURL?.toUri() })
                                imgViewContent2.setImageURI((count-1).let { layout?.cell?.get(it)?.imageURL?.toUri() })
                                imgViewContent3.setImageURI(count.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                            }
                        }
                    }
                    5 -> {
                        val imgViewContent1: ImageView = itemView.img_5_1
                        val imgViewContent2: ImageView = itemView.img_5_2
                        imgViewContent1.setImageURI(0.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                        imgViewContent2.setImageURI(1.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                    }
                    13 -> {
                        val imgViewContent1: ImageView = itemView.img_131
                        val imgViewContent2: ImageView = itemView.img_132
                        imgViewContent1.setImageURI(0.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                        imgViewContent2.setImageURI(1.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                    }
                    14 -> {
                        val imgViewContent1: ImageView = itemView.img_141
                        val imgViewContent2: ImageView = itemView.img_142
                        imgViewContent1.setImageURI(0.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                        imgViewContent2.setImageURI(1.let { layout?.cell?.get(it)?.imageURL?.toUri() })
                    }
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (num == TYPE_CONTENT4) {
            TYPE_CONTENT4
        }else if (num == TYPE_CONTENT5){
            TYPE_CONTENT5
        }else if (num == TYPE_CONTENT13){
            TYPE_CONTENT13
        }else{
            TYPE_CONTENT14
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_CONTENT4){
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_layout_4, parent , false)
            return contentViewHolder(inflate)
        }else if (viewType == TYPE_CONTENT5){
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_layout_5, parent , false)
            return contentViewHolder(inflate)
        }else if (viewType == TYPE_CONTENT13){
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_layout_13, parent , false)
            return contentViewHolder(inflate)
        }else{
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_layout_14, parent , false)
            return contentViewHolder(inflate)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as contentViewHolder).bind(list[position], num)
    }

    override fun getItemCount(): Int = list.size
}