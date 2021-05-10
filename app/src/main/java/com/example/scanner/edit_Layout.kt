package com.example.scanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.Adapter.layout_cell_adapter
import com.example.scanner.Model.ModelToPDF.Cell
import com.example.scanner.Model.ModelToPDF.Enum.CellType
import com.example.scanner.Model.ModelToPDF.Enum.LayoutType
import com.example.scanner.Model.ModelToPDF.Enum.Margin
import com.example.scanner.Model.ModelToPDF.Enum.Orientation
import com.example.scanner.Model.ModelToPDF.Layout
import com.example.scanner.Model.ModelToPDF.PageHeader
import com.example.scanner.Model.all_item_layout_pdf
import kotlinx.android.synthetic.main.activity_config_layout.*
import kotlinx.android.synthetic.main.activity_edit__layout.*
import java.io.File

class edit_Layout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__layout)
        val arrall = ArrayList<all_item_layout_pdf>()
        viewLayoutPDF(arrall, 1 , 4, 1)

    }

    fun viewLayoutPDF(arrall: ArrayList<all_item_layout_pdf> , numheader : Int , numcontent : Int , numfooter : Int){
        addLayout(arrall, numheader , numcontent , numfooter)
        img_cre.setOnClickListener {
            addLayout(arrall, numheader , numcontent , numfooter)
        }
    }

    fun addLayout(allLayout : ArrayList<all_item_layout_pdf> , numheader : Int , numcontent : Int , numfooter : Int){
        val arrCell = ArrayList<Cell>()
//        val url : String = "${getOutputDirectory()}/02_10_20.jpg"
//        val url1 : String =  "${getOutputDirectory()}/2021-04-27-14-33-34-884.jpg"
//        val url2 : String =  "${getOutputDirectory()}/2021-04-27-23-00-04-161.jpg"
//        arrCell.add(Cell(null , null , null , url , CellType.image ))
//        arrCell.add(Cell(null , null , null , url1 , CellType.image ))
        val arrLayout = ArrayList<Layout>()
        val pageheader = PageHeader()
        val pagefooter = PageHeader()
        pagefooter.text.text = (allLayout.count()+1).toString()
        arrLayout.add(Layout(arrLayout.count()-1, 1,1, Margin.Normal , arrCell , pageheader , pagefooter, orientation = Orientation.portrait , layoutType = LayoutType.pageBasic  ))
        allLayout.add(all_item_layout_pdf(arrLayout,arrLayout,arrLayout))
        rccv_layout.adapter = layout_cell_adapter(this, allLayout , numheader , numcontent , numfooter)
        rccv_layout.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false)
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}