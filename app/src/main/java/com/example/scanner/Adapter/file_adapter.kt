package com.example.scanner.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.scanner.Interface.IClickBtnMenu
import com.example.scanner.Model.item_File
import com.example.scanner.R
import kotlinx.android.synthetic.main.activity_itemfile.view.*

class file_adapter(val list: List <item_File>, val Iclickbtnmenu : IClickBtnMenu) : RecyclerView.Adapter<file_adapter.fileViewHolder>() {
    class fileViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgpage: ImageView = v.img
        val name: TextView = v.name
        val extension: TextView =v.extension
        val date: TextView = v.date_time
        val leght: TextView = v.leght
        val size: TextView = v.size
        val imgmenu: ImageView = v.menu
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): fileViewHolder {
        val viewHolder = LayoutInflater.from(p0.context).inflate(R.layout.activity_itemfile, p0 , false )
        return fileViewHolder(viewHolder)
    }

    override fun onBindViewHolder(p0: fileViewHolder, p1: Int) {
        val currenItem = list[p1]
        p0.imgpage.setImageResource(currenItem.image)
        p0.name.text = currenItem.name
        p0.extension.text = currenItem.extension
        p0.date.text = currenItem.date
        p0.leght.text = currenItem.leght
        p0.size.text = currenItem.size
        p0.imgmenu.setImageResource(currenItem.menu)
        p0.imgmenu.setOnClickListener {
            Iclickbtnmenu.ClickItemMenu(currenItem)
        }
    }

    override fun getItemCount() = list.size
}