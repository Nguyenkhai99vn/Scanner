package com.example.scanner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Interface.IclickBtnSelectImage
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.R
import kotlinx.android.synthetic.main.item_layout_edit.view.*

class layout_edit_adapter(val list: ArrayList<Layout>,
                          val context: Context,
                          val header : Int,
                          val footer : Int,
                          val iclickBtnSelectImage: IclickBtnSelectImage) :
    RecyclerView.Adapter<layout_edit_adapter.layout_edit_viewholder>() {

    class layout_edit_viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageheader1 : ImageView = itemView.img_header01
        val imageheader2 : ImageView = itemView.img_header02
        val edittextheader : EditText = itemView.edt_header
        val gridView : RecyclerView = itemView.rccv_content
        val edittextfooter : EditText = itemView.edt_footer
        val numfooter1 : TextView = itemView.tv_footer01
        val numfooter2 : TextView = itemView.tv_footer02
        val rltheader : RelativeLayout = itemView.rlt_header
        val rltfooter : RelativeLayout = itemView.rlt_footer
    }

    fun setData(layout: Layout) {
        list.add(layout)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): layout_edit_viewholder {
        val View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_edit, parent, false)
        return layout_edit_viewholder(View)
    }

    override fun onBindViewHolder(holder: layout_edit_viewholder, position: Int) {
        val currenitem = list[position]

        val gridAdapter = grid_content_adapter(currenitem.cell, context, currenitem , iclickBtnSelectImage)
        holder.gridView.layoutManager =
                GridLayoutManager(context, list[position].numofColms)
        holder.gridView.adapter = gridAdapter

        if (header == 1){
            holder.imageheader1.visibility = View.GONE
            holder.imageheader2.visibility = View.VISIBLE
            holder.edittextheader.visibility = View.GONE
            holder.imageheader2.setImageURI(currenitem.header?.imageURL?.toUri())
        }else if (header == 2){
            holder.imageheader1.visibility = View.VISIBLE
            holder.imageheader2.visibility = View.GONE
            holder.edittextheader.visibility = View.VISIBLE
            holder.imageheader1.setImageURI(currenitem.header?.imageURL?.toUri())
        }else{
            holder.rltheader.visibility = View.GONE
        }

        if (header == 1){
            holder.numfooter1.visibility = View.GONE
            holder.numfooter2.visibility = View.VISIBLE
            holder.edittextfooter.visibility = View.GONE
        }else if (header == 2){
            holder.numfooter1.visibility = View.VISIBLE
            holder.numfooter2.visibility = View.GONE
            holder.edittextfooter.visibility = View.VISIBLE
        }else{
            holder.rltfooter.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = list.size
}