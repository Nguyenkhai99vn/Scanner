package com.example.scanner

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.Adapter.file_adapter
import com.example.scanner.Interface.IClickBtnMenu
import com.example.scanner.Interface.IClickBtnSheetListener
import com.example.scanner.Model.item_File
import com.example.scanner.Model.item_sheet
import kotlinx.android.synthetic.main.activity_add_image.*
import kotlinx.android.synthetic.main.activity_itemfile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_menu.*
import okhttp3.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestListFile()
        btn_Action.setOnClickListener {
//            val intent = Intent(this@MainActivity, config_Layout::class.java)
//            startActivity(intent);
            clickBottonSheetFragment()
        }
        btn_Reload.setOnClickListener {
            finish()
            startActivity(getIntent())
        }
        val readFile = fileReader(getOutputDirectory())
        for (i in 0..(readFile.count()-1))
        {
            Log.d("File",readFile[i].toString() )
        }

    }
    fun requestListFile(){
        val arrFile = ArrayList<item_File>()
        val readFile = fileReader(getOutputFileDirectory())
        val simpleDateFormat : SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd 'at'HH:mm:ss ")
        val simpleDateFormat2 = SimpleDateFormat("yyyy.MM.d 'at'HH:mm", Locale.CHINA)

        for (i in 0..(readFile.count()-1))
        {
            val date  = Date(readFile[i].lastModified())
            val lastModDate : String = simpleDateFormat2.format(date)
//            val nameFile : String = simpleDateFormat.format(Date(readFile[i].lastModified()).toString())
            arrFile.add(item_File(R.drawable.anh,
                    readFile[i].name,
                    readFile[i].extension,
                    lastModDate,
                    "1",
                    getFileSize(readFile[i]),
                    R.drawable.menu_object))
        }
        rccv_file.adapter = file_adapter(arrFile , object : IClickBtnMenu{
            override fun ClickItemMenu(itemFile: item_File) {
                openMenuDialog(Gravity.BOTTOM , itemFile)
            }

        })
        rccv_file.layoutManager = LinearLayoutManager(rlthome.context)
    }
    fun openMenuDialog(gravity : Int , itemFile : item_File){
        val dialog : Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_menu)
        val window : Window? = dialog.window
        if (window == null) {
            return
        }else{
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT , WindowManager.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
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
        dialog.Delete.setOnClickListener {
        }
        dialog.Cancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun getFileSize(file: File): String{
        var size = file.length()
        if((size /1024) < 1024)
        {
            size = (file.length() / 1024)
            val s : String = "${size} KB"
            return s
        }else{
            size = ((file.length() / 1024)/1024)
            val s : String = "${size} MB"
            return s
        }
    }
    private fun clickBottonSheetFragment() {
        val arrsheet = ArrayList<item_sheet> ()
        arrsheet.add(item_sheet(R.drawable.file_pdf, "Conver to PDF", R.drawable.right))
        arrsheet.add(item_sheet(R.drawable.file_word, "Conver to WORD", R.drawable.right))
        arrsheet.add(item_sheet(R.drawable.file_txt, "Conver to TXT", R.drawable.right))
        val myBottonSheetFragment = BtnSheetSelectConvertFragment(arrsheet,
                object : IClickBtnSheetListener {
                    override fun clickItemSheet(itemSheet: item_sheet) {
                        Toast.makeText(this@MainActivity, itemSheet.content, Toast.LENGTH_SHORT).show()
                        val id = itemSheet.content
                        when (id) {
                            "Conver to PDF" -> {
                                val intent = Intent(this@MainActivity, edit_Layout::class.java)
                                var isSelect : Int = 1
                                intent.putExtra("convert", isSelect)
                                startActivity(intent);
                            }
                            "Conver to WORD" -> {
                                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                                var isSelect : Int = 2
                                intent.putExtra("convert", isSelect)
                                startActivity(intent);
                            }
                            "Conver to TXT" -> {
                                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                                var isSelect : Int = 3
                                intent.putExtra("convert", isSelect)
                                startActivity(intent);
                            }
                        }
                    }
                })
        myBottonSheetFragment.show(supportFragmentManager, myBottonSheetFragment.tag)
    }
    fun fileReader(root: File) : ArrayList<File> {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = root.listFiles()
        if (listAllFiles != null && listAllFiles.size > 0) {
            for (currentFile in listAllFiles) {
//                if (currentFile.name.endsWith(".docx")) {
//                    // File absolute path
                    fileList.add(currentFile.absoluteFile)
//                }
            }
        }
        return fileList
    }
    fun getOutputFileDirectory(): File {
        val datadir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        return if (datadir != null && datadir.exists())
            datadir else filesDir
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}
