package com.example.scanner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IClickItemLayout
import com.example.scanner.Model.item_layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.itemimage.view.*
import kotlinx.android.synthetic.main.itemlayout.view.*
import java.util.zip.Inflater

class layout_adapter( val list : List<item_layout> , val iClickItemLayout: IClickItemLayout ) : RecyclerView.Adapter<layout_adapter.viewholder>() {
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
            iClickItemLayout.onclick(currenitem,position)
            if (currenitem.isSelected == false)
            {
                currenitem.isSelected = true
                holder.select.visibility  = View.VISIBLE
            }else{
                currenitem.isSelected = false
                holder.select.visibility  = View.GONE
            }
        }

    }

    override fun getItemCount(): Int = list.size
}