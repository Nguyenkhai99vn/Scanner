package com.example.scanner.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.*
import com.example.scanner.Interface.IClickAddImage
import com.example.scanner.Interface.IclickBtnSelectImage
import com.example.scanner.Model.ModelToPDF.Cell
import com.example.scanner.Model.ModelToPDF.Enum.CellType
import com.example.scanner.Model.ModelToPDF.Enum.PageHeaderType
import com.example.scanner.Model.ModelToPDF.Layout
import kotlinx.android.synthetic.main.item_grid_layout.view.*
import kotlinx.android.synthetic.main.item_sheet_camera_img.*


class grid_content_adapter(val list: List<Cell>, val context: Context , val layout : Layout ,val iclickBtnSelectImage: IclickBtnSelectImage) :
    RecyclerView.Adapter<grid_content_adapter.gridcontentViewHolder>() {

    class gridcontentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cell: Cell, iClickAddImage: IClickAddImage , layout : Layout) {

            val text : TextView = itemView.tv_text
            val textheader : TextView = itemView.tv_textheader
            val textfooter : TextView = itemView.tv_textfooter
            var image : ImageView = itemView.img_image
            var width : Int = Resources.getSystem().displayMetrics.widthPixels
            var height : Int = Resources.getSystem().displayMetrics.heightPixels
            val col = layout.numofColms
            val row = layout.numofRows
            var magintop = 0
            var maginbotton = 0
            if (layout.header?.type != PageHeaderType.none){
                magintop = 150
            }
            if (layout.footer?.type != PageHeaderType.none){
                maginbotton = 150
            }
                if (cell.cellType == CellType.text){
                    text.visibility = View.VISIBLE
                    textheader.visibility = View.GONE
                    textfooter.visibility = View.GONE
                    image.visibility = View.GONE
                    val w: Int = width/col
                    val h: Int = (height - magintop - maginbotton - 400)/(row)
                    val parms = LinearLayout.LayoutParams(w, h)
                    text.setLayoutParams(parms)
                }else if (cell.cellType == CellType.image){
                    text.visibility = View.GONE
                    textheader.visibility = View.GONE
                    textfooter.visibility = View.GONE
                    image.visibility = View.VISIBLE

                    val w: Int = width/col
                    val h: Int = (height - magintop - maginbotton -400)/(row)
                    val parms = LinearLayout.LayoutParams(w, h)
                    image.setLayoutParams(parms)

                    val uri = cell.imageURL?.toUri()
                    image.setImageURI(uri)
                    image.setOnClickListener {
                        iClickAddImage.addImage(cell)
                    }
                }else if (cell.cellType == CellType.image_header){
                    text.visibility = View.GONE
                    textheader.visibility = View.VISIBLE
                    textfooter.visibility = View.GONE
                    image.visibility = View.VISIBLE

                    val w: Int = width/col
                    val h: Int = ((height - magintop - maginbotton - 400)/row)
                    val parmsT = LinearLayout.LayoutParams(w, h/5)
                    val parmsI = LinearLayout.LayoutParams(w, (h/5)*4)
                    image.setLayoutParams(parmsI)
                    textheader.setLayoutParams(parmsT)

                    val uri = cell.imageURL?.toUri()
                    image.setImageURI(uri)
                    image.setOnClickListener {
                        iClickAddImage.addImage(cell)
                    }
                }else if (cell.cellType == CellType.image_footer){
                    text.visibility = View.GONE
                    textheader.visibility = View.GONE
                    textfooter.visibility = View.VISIBLE
                    image.visibility = View.VISIBLE

                    val w: Int = width/col
                    val h: Int = ((height - magintop - maginbotton - 400)/row)
                    val parmsT = LinearLayout.LayoutParams(w, h/5)
                    val parmsI = LinearLayout.LayoutParams(w, (h/5)*4)
                    image.setLayoutParams(parmsI)
                    textfooter.setLayoutParams(parmsT)

                    val uri = cell.imageURL?.toUri()
                    image.setImageURI(uri)
                    image.setOnClickListener {
                        iClickAddImage.addImage(cell)
                    }
                }
            }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): gridcontentViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_layout, parent, false)
        return gridcontentViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: gridcontentViewHolder, position: Int) {
        val currenItem = list[position]
        holder.bind(currenItem, object : IClickAddImage {
            override fun addImage(cell: Cell) {
                openMenuDialog(Gravity.BOTTOM, currenItem)
            }
        }, layout)
    }

    override fun getItemCount(): Int = list.size
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        val mContext = edit_Layout::class.java
//        super.onAttachedToRecyclerView(recyclerView)
//    }


    fun openMenuDialog(gravity: Int , cell: Cell){
        val dialog : Dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.item_sheet_camera_img)
        val window : Window? = dialog.window
        if (window == null) {
            return
        }else{
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowAttributes : WindowManager.LayoutParams = window.attributes
            windowAttributes.gravity = gravity
            window.attributes = windowAttributes
        }

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true)
        }else{
            dialog.setCancelable(false)
        }
        dialog.show()
        dialog.rlt_camera.setOnClickListener {
            val intent = Intent(context, Camera2Activity::class.java)
            startActivity(context, intent, Bundle.EMPTY)
        }
        dialog.rlt_galery.setOnClickListener {
            iclickBtnSelectImage.clickItemSelectImage(cell)
//            val intent = Intent(context, AddImage::class.java)
//            var isSelect : Int = 4
//            intent.putExtra("convert", isSelect)
//            startActivity(context, intent, Bundle.EMPTY)

        }
    }
}