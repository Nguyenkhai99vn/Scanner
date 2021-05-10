package com.example.scanner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IClickItemLayoutListener
import com.example.scanner.Model.item_layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.itemimage.view.*
import kotlinx.android.synthetic.main.itemlayout.view.*
import java.util.zip.Inflater

class layout_adapter( val list : List<item_layout> ) : RecyclerView.Adapter<layout_adapter.viewholder>() {
    class viewholder(v : View) : RecyclerView.ViewHolder(v) {
        val image : ImageView = v.image_Layout
        val select : ImageView = v.selectl
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val viewHolder =LayoutInflater.from(parent.context).inflate(R.layout.itemlayout , parent , false)
        return viewholder(viewHolder)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currenitem = list[position]
        holder.image.setImageResource(currenitem.image)
        holder.select.setImageResource(currenitem.selectL)
        holder.itemView.setOnClickListener {
            if (currenitem.isSelected == false)
            {
                holder.select.visibility  = View.VISIBLE
                currenitem.isSelected = true
            }else{
                holder.select.visibility  = View.GONE
                currenitem.isSelected = false
            }
        }
    }

    override fun getItemCount(): Int = list.size
}