package com.example.scanner

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.scanner.API.workAPI
import com.example.scanner.API.workService
import com.example.scanner.Adapter.addimage_adapter
import com.example.scanner.Adapter.layout_adapter
import com.example.scanner.Interface.IClickItemImageListener
import com.example.scanner.Model.item_image
import kotlinx.android.synthetic.main.activity_add_image.*
import kotlinx.android.synthetic.main.fragment_list_layout.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddImage : AppCompatActivity() {
    val arrImageUri = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)
        requestListImage()
        btn_finish.setOnClickListener {
            uploadImage(arrImageUri)
            val intent: Intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun uploadImage(arrdirec: ArrayList<String>) {
        val intent: Intent = getIntent();
        val isSelect = intent.getIntExtra("convert", 0 );
        var listPath = ArrayList<MultipartBody.Part>()
        for (i in 0..(arrdirec.count() -1))
        {
            val file = File(arrdirec[i])
            val requestbody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data") , file)
            val parts : MultipartBody.Part = MultipartBody.Part.createFormData("file" , file.name, requestbody)
            listPath.add(parts)
        }
        val retrofit = workService().retrofit()
        val uploadImage : workAPI = retrofit.create(workAPI::class.java)
        when(isSelect) {
            2 -> {
                val call: Call<String> = uploadImage.uploadImageToWord(listPath)
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val url = response.body().toString()
                            Log.e("Image upload success", url)
                            val urlDowload = "http://doczy.net/scantoword/${url}"
                            dowloadImage(urlDowload)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("Response fail", t.localizedMessage)
                    }

                })
            }
            3 -> {
                val call: Call<String> = uploadImage.uploadImageToText(listPath)
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val url = response.body().toString()
                            Log.e("Image upload success", url)
                            val urlDowload = "http://doczy.net/scantoword/${url}"
                            dowloadImage(urlDowload)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("Response fail", t.localizedMessage)
                    }

                })
            }
        }
    }

    private fun dowloadImage(url : String) {
        val getUrl : String = url
        val request : DownloadManager.Request = DownloadManager.Request(Uri.parse(getUrl))
        val title: String = URLUtil.guessFileName(getUrl , null , null)
        request.setTitle(title)
//        request.setDescription("Dowloading File Please wait....")
        val cookie : String? = CookieManager.getInstance().getCookie(getUrl)
        request.addRequestHeader("Cookie",cookie)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
        request.setDestinationInExternalFilesDir(this , Environment.DIRECTORY_DOWNLOADS , title)

        val dowloadmanager: DownloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dowloadmanager.enqueue(request)
//        Toast.makeText(this , "Dowload Completed" , Toast.LENGTH_SHORT).show()
    }
    fun requestListImage(): ArrayList<String>{
        val arrImage = ArrayList<item_image>()
        val readFile = imageReader(getOutputDirectory())
        for (i in 0..(readFile.count()-1))
        {
            arrImage.add(item_image(readFile[i].path , R.drawable.done))
        }
        rccv_Image.adapter = addimage_adapter(arrImage, object : IClickItemImageListener{
            override fun clickItemimage(itemImage: item_image) {
                for (i in 0..(readFile.count()-1)) {
                    if (itemImage.uri == readFile[i].path ) {
                        if (itemImage.isSelected == false){
                            arrImageUri.add(readFile[i].path)
                        }else if (itemImage.isSelected == true){
                            arrImageUri.remove(readFile[i].path)
                        }
                    }
                }
            }

        })
        rccv_Image.layoutManager = GridLayoutManager(this , 3)
        return arrImageUri
    }
    fun imageReader(root: File) : ArrayList<File> {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = root.listFiles()
        if (listAllFiles != null && listAllFiles.size > 0) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpg")) {
                    // File absolute path
                    fileList.add(currentFile.absoluteFile)
                }
            }
        }
        return fileList
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}