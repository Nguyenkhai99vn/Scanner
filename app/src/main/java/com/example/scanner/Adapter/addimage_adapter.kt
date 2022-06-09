package com.example.scanner.Adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IClickItemImageListener
import com.example.scanner.Model.item_image
import com.example.scanner.Model.item_layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.itemimage.view.*
import kotlinx.android.synthetic.main.itemlayout.view.*
import java.io.File


class addimage_adapter( val list : List<item_image> , val iClickItemImageListener: IClickItemImageListener) : RecyclerView.Adapter<addimage_adapter.viewholder>() {
    class viewholder(v : View) : RecyclerView.ViewHolder(v) {
        val image : ImageView = v.imageView
        val select : ImageView = v.select

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.itemimage , parent , false)
        return viewholder(viewHolder)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currenitem = list[position]
        holder.image.setImageURI(currenitem.uri.toUri())
        holder.select.setImageResource(currenitem.select)
        holder.itemView.setOnClickListener {
            iClickItemImageListener.clickItemimage(currenitem)
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