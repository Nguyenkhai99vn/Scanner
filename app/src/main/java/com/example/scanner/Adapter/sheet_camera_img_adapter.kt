package com.example.scanner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IClickBtnSheetListener
import com.example.scanner.Model.item_image_camera
import com.example.scanner.Model.item_sheet
import com.example.scanner.R
import kotlinx.android.synthetic.main.item_sheet_camera_img.view.*
import kotlinx.android.synthetic.main.itemsheet.view.*

class sheet_camera_img_adapter(val list: List<item_image_camera>, val iClickBtnSheetListener: IClickBtnSheetListener) : RecyclerView.Adapter<sheet_camera_img_adapter.sheetViewHolder>() {
    class sheetViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val avatar: ImageView = v.img_avatar
        val content: TextView = v.tv_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sheetViewHolder {
        val viewholder = LayoutInflater.from(parent.context).inflate(R.layout.item_sheet_camera_img , parent , false)
        return sheetViewHolder(viewholder)
    }

    override fun onBindViewHolder(holder: sheetViewHolder, position: Int) {
        val currenItem = list[position]
        holder.avatar.setImageResource(currenItem.avatar)
        holder.content.text = currenItem.name
        holder.itemView.setOnClickListener {
            iClickBtnSheetListener.clickItemSheetSelectImage(currenItem)
        }
    }

    override fun getItemCount(): Int = list.size
}