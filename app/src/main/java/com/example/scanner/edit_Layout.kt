package com.example.scanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.Adapter.layout_cell_adapter
import com.example.scanner.Model.ModelToPDF.*
import com.example.scanner.Model.ModelToPDF.Enum.*
import com.example.scanner.Model.all_item_layout_pdf
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_config_layout.*
import kotlinx.android.synthetic.main.activity_edit__layout.*
import java.io.File

class edit_Layout : AppCompatActivity() {
    lateinit var realm : Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__layout)

        Realm.init(this)
        val config : RealmConfiguration = RealmConfiguration.
        Builder().
        allowWritesOnUiThread(true).
        name("realmpdf.realm").
        build()
        realm = Realm.getInstance(config)

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

        val arrLayout = ArrayList<Layout>()

        val url : String = "${getOutputDirectory()}/02_10_20.jpg"
        val url1 : String =  "${getOutputDirectory()}/2021-04-27-14-33-34-884.jpg"
        val url2 : String =  "${getOutputDirectory()}/2021-04-27-23-00-04-161.jpg"



        //set Data Object CellObject
        val arrCell = ArrayList<Cell>()
        arrCell.add(Cell(null , null , null , url , CellType.image ))
        arrCell.add(Cell(null , null , null , url1 , CellType.image ))
        arrCell.add(Cell(null , null , null , url2 , CellType.image ))
        for (i in arrCell.indices){
            setCellObj(arrCell[i])
        }

//        set data object Pageheader for header
        val header = PageHeader()
        header.type = PageHeaderType.image_text
        val text1 = Text(1,"Page Header",null,Position.Top,null,0)
        header.text = text1
        settextObj(header.text)
        header.imageURL = url
        header.alignment = Position.Top
        setPageHeaderObj(header)

        //set data object Pageheader for footer
        val footer = PageHeader()
        footer.type = PageHeaderType.text
        val text2 = Text(1,"Page Footer",null,Position.Top,null,0)
        footer.text = text2
        settextObj(header.text)
        footer.imageURL = null
        footer.alignment = Position.Center
        setPageHeaderObj(footer)

        //get data object

        arrLayout.add(Layout(allLayout.count()+1,1,1,
                Margin.Normal,
                getCellObj() ,
                getPageHeaderObj()[0],
                getPageHeaderObj()[1],
                Orientation.portrait,
                LayoutType.pageBasic))
        Log.d("num footer" , getPageHeaderObj()[1].text.text.toString() )
        allLayout.add(all_item_layout_pdf(arrLayout,arrLayout,arrLayout))
        rccv_layout.adapter = layout_cell_adapter(this, allLayout , numheader , numcontent , numfooter)
        rccv_layout.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false)
    }


    fun settextObj(text : Text) {
        val temp = text.toRealmObject()
        realm.executeTransaction{
            realm.copyToRealm(temp)
        }
    }
    fun gettextObj(): ArrayList<Text> {
        val arrTextObj : List<TextObject> = realm.where(TextObject::class.java).findAll()
        val arrText = ArrayList<Text>()
        var text = Text()
        for (i in arrTextObj.indices){
            text = arrTextObj[i].getObject()
            arrText.add(text)
        }
        return arrText
    }

    fun setPageHeaderObj(pageHeader: PageHeader) {
        val temp = pageHeader.toRealmObject()
        realm.executeTransaction{
            realm.copyToRealm(temp)
        }
    }
    fun getPageHeaderObj(): ArrayList<PageHeader> {
        val arrPageheaderObj : List<HeaderObject> = realm.where(HeaderObject::class.java).findAll()
        val arrPageHeader = ArrayList<PageHeader>()
        var PageHeader = PageHeader()
        for (i in arrPageheaderObj.indices){
            PageHeader = arrPageheaderObj[i].getObject()
            PageHeader.text = gettextObj()[i]
            arrPageHeader.add(PageHeader)
        }
        return arrPageHeader
    }

    fun setCellObj(cell: Cell) {
        val temp = cell.toRealmObject()
        realm.executeTransaction{
            realm.copyToRealm(temp)
        }
    }
    fun getCellObj(): ArrayList<Cell> {
        val arrCellObj : List<CellObject> = realm.where(CellObject::class.java).findAll()
        val arrCell = ArrayList<Cell>()
        var Cell = Cell()
        for (i in arrCellObj.indices){
            Cell = arrCellObj[i].getObject()
            arrCell.add(Cell)
        }
        return arrCell
    }


    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}