package com.example.scanner.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IClickBtnSheetListener
import com.example.scanner.Model.item_sheet
import com.example.scanner.R
import kotlinx.android.synthetic.main.itemsheet.view.*

class sheet_adapter(val list: List<item_sheet> , val iClickBtnSheetListener: IClickBtnSheetListener) : RecyclerView.Adapter<sheet_adapter.sheetViewHolder>() {
    class sheetViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val avatar: ImageView = v.img_avt_sheet
        val content: TextView = v.txt_sheet
        val type: ImageView = v.img_type_sheet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sheetViewHolder {
        val viewholder = LayoutInflater.from(parent.context).inflate(R.layout.itemsheet , parent , false)
        return sheetViewHolder(viewholder)
    }

    override fun onBindViewHolder(holder: sheetViewHolder, position: Int) {
        val currenItem = list[position]
        holder.avatar.setImageResource(currenItem.avatar)
        holder.content.text = currenItem.content
        holder.type.setImageResource(currenItem.type)
        holder.itemView.setOnClickListener {
            iClickBtnSheetListener.clickItemSheet(currenItem)
        }
    }

    override fun getItemCount(): Int = list.size
}