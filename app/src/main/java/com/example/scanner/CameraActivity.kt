package com.example.scanner


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors
import androidx.camera.core.*
import 	androidx.camera.core.ImageCapture.OutputFileOptions.Builder
import androidx.camera.lifecycle.ProcessCameraProvider
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
typealias LumaListener = (luma: Double) -> Unit


class CameraActivity : AppCompatActivity()  {

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        // hide the action bar
        supportActionBar?.hide()

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listener for take photo button
        camera_capture_button.setOnClickListener { takePhoto() }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        var type = onType()

        btn_document.setOnClickListener {
            btn_document.setTextColor(resources.getColor(R.color.white))
            btn_document.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            btn_word.setTextColor(resources.getColor(R.color.type))
            btn_word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            btn_text.setTextColor(resources.getColor(R.color.type))
            btn_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            type =1
        }
        btn_word.setOnClickListener {
            btn_document.setTextColor(resources.getColor(R.color.type))
            btn_document.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            btn_word.setTextColor(resources.getColor(R.color.white))
            btn_word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            btn_text.setTextColor(resources.getColor(R.color.type))
            btn_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            type =2
        }
        btn_text.setOnClickListener {
            btn_document.setTextColor(resources.getColor(R.color.type))
            btn_document.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            btn_word.setTextColor(resources.getColor(R.color.type))
            btn_word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            btn_text.setTextColor(resources.getColor(R.color.white))
            btn_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            type =3
        }
        iv_capture.setOnClickListener {
            val intent = Intent(this , create_layout::class.java)
            intent.putExtra("convert" , type)
            startActivity(intent)
        }
        closeCamera.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            finish()
            startActivity(intent)
        }

    }
    fun onType(): Int{
        val intent: Intent = getIntent();
        val isSelect = intent.getIntExtra("convert", 0 );
        var temp :Int = 0
        when(isSelect){
            1->{
                btn_document.setTextColor(resources.getColor(R.color.white))
                btn_document.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                temp =1
            }
            2->{
                btn_word.setTextColor(resources.getColor(R.color.white))
                btn_word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                temp =2
            }
            3->{
                btn_text.setTextColor(resources.getColor(R.color.white))
                btn_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                temp =3
            }
        }
        return  temp
        }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US
                ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)

                // set the saved uri to the image view
                iv_capture.visibility = View.VISIBLE
                iv_capture.setImageURI(savedUri)
                val msg = "Photo capture succeeded: $savedUri"
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                Log.d(TAG, msg)
            }
        })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                    }

            imageCapture = ImageCapture.Builder()
                    .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                            Log.d(TAG, "Average luminosity: $luma")
                        })
                    }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture, imageAnalyzer)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults:
            IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        listener(luma)

        image.close()
    }
}