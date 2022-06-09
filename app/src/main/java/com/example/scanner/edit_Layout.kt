package com.example.scanner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.API.cellAPI
import com.example.scanner.API.cellService
import com.example.scanner.Adapter.layout_edit_adapter
import com.example.scanner.Interface.IclickBtnSelectImage
import com.example.scanner.Interface.layoutCallBack
import com.example.scanner.Model.ModelToPDF.*
import com.example.scanner.Model.ModelToPDF.Enum.*
import com.example.scanner.Model.ModelToPDF.Enum.all_item_layout_pdf
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_edit__layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class edit_Layout : AppCompatActivity() {
    lateinit var realm : Realm
    private val cell:cellAPI = cellService().retrofit().create(cellAPI::class.java)
    lateinit var uri : String
    lateinit var adduri : String
     var numheader : Int = 0
     var numfooter : Int = 0
    var arrlayout = ArrayList<Layout>()
    var arruri = ArrayList<String>()
    val realmManager = RealmManager()
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
        val intent : Intent = getIntent()
        val numcontent = intent.getIntExtra("numcontent" , 0)
        Log.e("numcontent" , numcontent.toString())
        uri = intent.getStringExtra("uri").toString()
        arruri.add(uri)
        adduri = intent.getStringExtra("adduri").toString()
        Log.e("uri", uri)
        numheader = intent.getIntExtra("header" , 0)
        numfooter = intent.getIntExtra("footer" , 0)
        val item = Item()
        arrlayout = viewLayoutEdit(this,item, numheader, numcontent, numfooter)

//        viewLayoutPDF(this, item, numheader, numcontent , numfooter)
    }

    private fun fetchCellData(layoutID : Int , layoutcallback: layoutCallBack) {
        val service:Call<List<FetchLayout>> = cell.getCell(layoutID)
        val layout = Layout()
        service.enqueue(object : Callback<List<FetchLayout>>{
            override fun onResponse(call: Call<List<FetchLayout>>, response: Response<List<FetchLayout>>) {
                val FetchLayout : ArrayList<FetchLayout> = (response.body() as ArrayList<FetchLayout>?)!!
                val celltype : ArrayList<cellType> = FetchLayout[0].cell
                val cell = ArrayList<Cell>()
                    layout.layoutID = FetchLayout[0].layoutID
                    layout.numofColms = FetchLayout[0].numColms
                    layout.numofRows = FetchLayout[0].numRows

                    for (i in 0 .. celltype.count()-1){
                        if (celltype[i].cellType == 1) {
                            cell.add(Cell(null,null,null,null,CellType.text))
                        }else if (celltype[i].cellType == 2){
                            cell.add(Cell(null,null,null,null,CellType.image))
                        }else if (celltype[i].cellType == 3){
                            cell.add(Cell(null,null,null,null,CellType.image_header))
                        }else if (celltype[i].cellType == 4){
                            cell.add(Cell(null,null,null,null,CellType.image_footer))
                        }
                    }
                    layout.cell = cell
                    layoutcallback.onSuccess(layout)

            }

            override fun onFailure(call: Call<List<FetchLayout>>, t: Throwable) {
                Log.e("Response fail", t.localizedMessage)
                layoutcallback.onError(t)
            }
        })

    }
    private fun viewLayoutEdit(context: Context, item : Item, numheader : Int , numcontent : Int, numfooter : Int) : ArrayList<Layout>{
        val arraylayout = ArrayList<Layout>()
        fetchCellData(numcontent, object : layoutCallBack {
            override fun onSuccess(layout: Layout) {
                val adapter = layout_edit_adapter(arraylayout, context, numheader, numfooter,
                    object : IclickBtnSelectImage{
                        override fun clickItemSelectImage(cell: Cell) {
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = "image/*"
                            startActivityForResult(intent,123)
                        }

                    })
                Log.e("iddd", layout.layoutID.toString())
                Log.e("col", layout.numofColms.toString())
                Log.e("row", layout.numofRows.toString())
                val cell = layout.cell

                addheaderfooter(layout, numheader, numfooter)
                val max = layout.numofRows * layout.numofColms
                var j = 0
                if (arruri.count() <= max) {
                    for (i in 0..layout.cell.count() - 1) {
                        if (layout.cell[i].cellType == CellType.text) {
                            layout.cell[i].imageURL = ""
                        } else {
                            if (j < arruri.count()) {
                                Log.e("uri", arruri[j])
                                layout.cell[i].imageURL = arruri[j]
                                j++
                            }
                        }
                    }
                }
                Log.e("layout2", cell[0].imageURL.toString())
                arraylayout.add(layout)
                img_cre.setOnClickListener {
                    adapter.setData(layout)
                }
                img_export.setOnClickListener {
                    exportPDF(realmManager, layout)
                }

                rccv_layout.adapter = adapter
                rccv_layout.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onError(throwable: Throwable) {

            }

        })
        return arraylayout
    }

    fun exportPDF(realmManager: RealmManager , layout: Layout){
        addLayout(realmManager)
        layout.magin = Margin.Normal
        layout.cell =  realmManager.getCellObj(realm)
        layout.header = realmManager.getPageHeaderObj(realm)[0]
        layout.footer = realmManager.getPageHeaderObj(realm)[1]
        layout.orientation = Orientation.portrait
        layout.layoutType = LayoutType.pageBasic
        val item = Item()
        val imgArr = ArrayList<String>()
        val textArr = ArrayList<Text>()
        for (i in 0..realmManager.getCellObj(realm).count()-1){
            if (realmManager.getCellObj(realm)[i].cellType == CellType.image || realmManager.getCellObj(realm)[i].cellType == CellType.image_header || realmManager.getCellObj(realm)[i].cellType == CellType.image_footer){
                imgArr.add(realmManager.getCellObj(realm)[i].imageURL.toString())
            }
        }
        for (i in 0..imgArr.count()-1){
            item.subItems.add(DetailItem(imgArr[i]))
        }
        for (i in 0..item.subItems.count()-1){
            Log.d("arrCell" , item.subItems[i].url)
        }


        item.pageSize = PageSize(500.toFloat(),800.toFloat())
        item.pageHeader = realmManager.getPageHeaderObj(realm)[0]
        item.pageFooter = realmManager.getPageHeaderObj(realm)[1]
        item.defaultLayout = layout
        item.imageScale = ImageScale.fit

        for (i in 0..item.defaultLayout.cell!!.count()-1){
            val cell = item.defaultLayout.cell!![i]
            if (cell.text != null){
                textArr.add(cell.text!!)
            }
        }
        item.texts = textArr
        val myPDFExport = MyPDFExport()
        myPDFExport.drawPDF(item,this,getOutputDirectory())
    }

    fun addLayout(realmManager: RealmManager){
        realmManager.deleteCellAll(realm)
        realmManager.deleteHeaderAll(realm)
        realmManager.deleteTextAll(realm)
//        val url : String = "${getOutputDirectory()}/unnamed.png"
//        val url1 : String =  "${getOutputDirectory()}/2021-04-27-14-33-34-884.jpg"
//        val url2 : String =  "${getOutputDirectory()}/2021-04-27-23-00-04-161.jpg"
        val url1 = "${getOutputDirectory()}/image1.jpg"
        val url2 = "${getOutputDirectory()}/image2.jpg"
        val url3 = "${getOutputDirectory()}/image3.jpg"
        val url4 = "${getOutputDirectory()}/image4.jpg"
        val url5 = "${getOutputDirectory()}/image5.png"
        val url6 = "${getOutputDirectory()}/image6.png"

        val arrCell = ArrayList<Cell>()
        val one = Text(1, "Image number one", null, Position.Top, null, 0)
        val two = Text(1, "Image Number Two", null, Position.Top, null, 0)
        val three = Text(1, "Image Number Three", null, Position.Top, null, 0)
        val four = Text(1, "Image Number Four", null, Position.Top, null, 0)
        val five = Text(1, "Image Number Five", null, Position.Top, null, 0)
        val six = Text(1, "Image Number Six", null, Position.Top, null, 0)

//        arrCell.add(Cell(null, null, one, url2, CellType.image_header))
//        arrCell.add(Cell(null, null, two, url4, CellType.image_header))
//        arrCell.add(Cell(null, null, three,url5, CellType.image_header))
//        arrCell.add(Cell(null, null, four,url6, CellType.image_header))
//        arrCell.add(Cell(null, null, five,url5, CellType.image_header))
//        arrCell.add(Cell(null, null, six, url6, CellType.image_header))
//        arrCell.add(Cell(null, null, one,url2, CellType.image_header))
//        arrCell.add(Cell(null, null, two,url4, CellType.image_header))
        arrCell.add(Cell(null, null, one, null, CellType.text))
        arrCell.add(Cell(null, null, two, null, CellType.text))
        arrCell.add(Cell(null, null, three,url2, CellType.image))
        arrCell.add(Cell(null, null, four,url4, CellType.image))
        arrCell.add(Cell(null, null, five,null, CellType.text))
        arrCell.add(Cell(null, null, six, null, CellType.text))
        arrCell.add(Cell(null, null, one,url5, CellType.image))
        arrCell.add(Cell(null, null, two,url6, CellType.image))

        for (i in arrCell.indices){
            realmManager.setCellObj(realm , arrCell[i])
        }

        val text1 = Text(1, "Page Header", null, Position.Top, null, 0)
        val text2 = Text(1, "Page Footer", null, Position.Top, null, 0)
        val header = PageHeader()
        header.type = PageHeaderType.image_text
        header.text = text1
        realmManager.settextObj(realm,header.text)
        header.imageURL = url1
        header.alignment = Position.Top
        realmManager.setPageHeaderObj(realm, header)

        val footer = PageHeader()
        footer.type = PageHeaderType.text
        footer.text = text2
        realmManager.settextObj(realm, header.text)
        footer.imageURL = null
        footer.alignment = Position.Center
        realmManager.setPageHeaderObj(realm, footer)

    }


    private fun addheaderfooter(layout : Layout , numheader: Int , numfooter: Int){
        var header = PageHeader()
        var footer = PageHeader()
        if (numheader == 1){
            header.type =PageHeaderType.image
            layout.header = header
        }else if(numheader == 2){
            header.type =PageHeaderType.image_text
            layout.header = header
        }else{
            header.type =PageHeaderType.none
            layout.header = header
        }
        if (numfooter == 1){
            footer.type =PageHeaderType.pageNum
            layout.footer = footer
        }else if(numfooter == 2){
            footer.type =PageHeaderType.text
            layout.footer = footer
        }else{
            footer.type =PageHeaderType.none
            layout.footer = footer
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123){

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("layoutID" , arrlayout[0].layoutID)
        outState.putInt("numofColms" , arrlayout[0].numofColms)
        outState.putInt("numofRows" , arrlayout[0].numofRows)
        outState.putInt("numheader" , numheader)
        outState.putInt("numfooter" , numfooter)
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}