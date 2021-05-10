package com.example.scanner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.fragment_footer_1.view.*
import kotlinx.android.synthetic.main.fragment_footer_2.view.*
import kotlinx.android.synthetic.main.fragment_footer_2.view.tv_footer
import kotlinx.android.synthetic.main.fragment_header_1.view.*
import kotlinx.android.synthetic.main.fragment_header_2.view.*
import java.util.zip.Inflater

private const val TYPE_FOOTER1 : Int = 1
private const val TYPE_FOOTER2 : Int = 2

class layout_footer_adapter(private val list: List<Layout> ,private val num : Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class footerViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {
        fun bind(layout: Layout , num: Int) {
            when (num) {
                2 -> {
                    var tvNum: TextView = itemView.tv_footer
                    tvNum.text = layout.footer?.text?.text
                }
                1 -> {
                    var tvNum: TextView = itemView.tv_footer
                    tvNum.text = layout.footer?.text?.text
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (num == TYPE_FOOTER1)
        {
            TYPE_FOOTER1
        }else{
            TYPE_FOOTER2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_FOOTER2){
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_footer_2, parent , false)
            return footerViewHolder(inflate)
        }else{
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.fragment_footer_1, parent , false)
            return footerViewHolder(inflate)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as footerViewHolder).bind(list[position], num)
    }

    override fun getItemCount(): Int = list.size
}