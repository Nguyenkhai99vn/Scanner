package com.example.scanner

import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.blue
import com.example.scanner.Model.ModelToPDF.Enum.*
import com.example.scanner.Model.ModelToPDF.Index
import com.example.scanner.Model.ModelToPDF.Item
import com.example.scanner.Model.ModelToPDF.countIT
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MyPDFExport {

    fun drawPDF(item: Item, context: Context , filePath: File){
        var imgCount = 0
        var textCount = 0
        val document = PdfDocument()
        val pageWidth = item.pageSize.width.toInt()
        val pageHeight = item.pageSize.height.toInt()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val pageNum = item.getTotalPage()
        val layout = item.defaultLayout
        val matrix = calcultateCell(item)
        Log.d("height", matrix.height.toString())
        Log.d("width", matrix.width.toString())

        for (i in 0..pageNum-1){
            var indexMatrix = 0
            val page = document.startPage(pageInfo)
            val canvas : Canvas = page.canvas
            if (item.pageHeader.type != PageHeaderType.none){
                drawHeader(item, canvas)
            }
            if (item.pageFooter.type != PageHeaderType.none){
                drawFooter(item, matrix, canvas, i+1)
            }
            for (x in 0..layout.numofRows-1){
                for (y in 0..layout.numofColms-1){
                    val maxCell = layout.cell!!.count()
                    if (indexMatrix < maxCell ){
                        val cell = layout.cell!![indexMatrix]
                        if (cell?.cellType == CellType.image){
                            imgCount = drawImageCell(item, matrix ,canvas , indexMatrix, imgCount)
                        }else if (cell?.cellType == CellType.text){
                            textCount = drawTextCell(item, matrix ,canvas, indexMatrix, textCount)
                        }else if (cell?.cellType == CellType.image_header){
                            val arr = drawImageHeaderCell(item, matrix, indexMatrix ,canvas , textCount, imgCount)
                            textCount = arr[0].countT
                            imgCount = arr[0].countI
                        }else if (cell?.cellType == CellType.image_footer){
                            val arr = drawImageFooterCell(item, matrix, indexMatrix ,canvas, textCount, imgCount)
                            textCount = arr[0].countT
                            imgCount = arr[0].countI
                        }
                        indexMatrix += 1
                    }

                }
            }
            document.finishPage(page)
        }

        try {
            val file = File(filePath, "pdfsend1.pdf")
            Log.d("file", file.path)
            var os = FileOutputStream(file)
            document.writeTo(os)
            document.close()
            os.close()
        } catch (e: IOException) {
            Toast.makeText(context, "Error generating file", Toast.LENGTH_LONG).show()
        }
    }

    fun drawHeader(item: Item , canvas: Canvas){
        val logoWH = 50F
        val y = 60F
        val imgURL = item.pageHeader.imageURL
        Log.d("header" , imgURL.toString())
        Log.d("header" , item.pageHeader.text.text.toString())
        Log.d("header" , item.pageHeader.type.toString())
        val width = item.pageSize.width
        when(item.pageHeader.type){
            PageHeaderType.image -> {
                drawImageHeader(imgURL.toString(), 0F, 10F, width, y -20,canvas , item.imageSolution)
                drawLine(0F, y, width, y, canvas, Color.GRAY)
            }
            PageHeaderType.image_text -> {
                val imgWidth = drawImageHeader(imgURL.toString(), 15F , 10F, y - 10, y - 20 , canvas, item.imageSolution)
                val text = item.pageHeader.text.text.toString()
                Log.d("text", text)
                drawtext(text, (imgWidth.width + 20F), 0F, (width- imgWidth.width  - 30), y , canvas, 25F, Position.Left)
                drawLine(0F, y, width, y, canvas, Color.GRAY)
            }
        }
    }

    fun drawFooter(item: Item, matrix: Index, canvas: Canvas, pageNum: Int){
        val pageNumWH = 30F
        val width = item.pageSize.width
        val y = matrix.y[matrix.y.count()-1] + matrix.height + 15
        val yT = item.pageSize.height - y
        when(item.pageFooter.type){
            PageHeaderType.pageNum -> {
                val originX = width / 2 - 25
                drawtext(pageNum.toString(), originX, y, pageNumWH, yT , canvas, 30F, Position.Center)
                drawLine(0F, y, width, y, canvas, Color.GRAY)
            }
            PageHeaderType.text -> {
                val pageNumX = width - pageNumWH
                drawtext(pageNum.toString(), pageNumX, y, pageNumWH, yT , canvas, 25F, Position.Center)
                val footerText = item.pageFooter.text.text
                drawtext(footerText.toString(), 0F, y, width - 45, yT , canvas, 25F, Position.Left)
                drawLine(0F, y, width, y, canvas, Color.GRAY)
            }
        }
    }

    fun drawImageHeaderCell(item: Item, matrix: Index, indexMatrix: Int , canvas: Canvas, textCount: Int, imgCount: Int) : ArrayList<countIT>{
        var countT = textCount
        var countI = imgCount
        var isText = true
        var arrTI = ArrayList<countIT>()
        val text = item.defaultLayout.cell!![textCount].text?.text
        for(i in 0..1){
            if (isText){
                isText = false
                while (textCount < item.texts.count()){
                    val text = item.texts[textCount].text
                    Log.d("textheader" , text.toString())
                    drawtext(text.toString(), matrix.x[indexMatrix], matrix.y[indexMatrix], matrix.width, matrix.height / 5, canvas, 20F, Position.Center)
                    countT += 1
                    break
                }
            }else{
                isText = true
                while (imgCount < item.subItems.count()){
                    val subItem = item.subItems[imgCount]
                    val url = subItem.url
                    val imgHeight = matrix.height - matrix.height/5
                    val x = matrix.x[indexMatrix]
                    val y = matrix.y[indexMatrix] + matrix.height/5
                    drawImage(url, x, y, matrix.width, imgHeight, canvas, item.imageSolution, item.imageScale)
                    countI += 1
                    break
                }
            }
        }

        Log.d("arrImage" ,countT.toString())
        Log.d("arrText" , countI.toString())
        arrTI.add(countIT(countT, countI))

        return arrTI
    }

    fun drawImageFooterCell(item: Item, matrix: Index, indexMatrix: Int , canvas: Canvas, textCount: Int, imgCount: Int) : ArrayList<countIT>{
        var countT = textCount
        var countI = imgCount
        var isText = true
        var arrTI = ArrayList<countIT>()
        for(i in 0..1){
            if (isText){
                isText = false
                while (imgCount < item.subItems.count()){
                    val subItem = item.subItems[imgCount]
                    val url = subItem.url
                    val imgHeight = matrix.height - matrix.height/5
                    val x = matrix.x[indexMatrix]
                    drawImage(url, x, matrix.y[indexMatrix], matrix.width, imgHeight, canvas, item.imageSolution, item.imageScale)
                    countI += 1
                    break
                }
            }else{
                isText = true
                val text = item.texts[textCount].text
                val y = matrix.y[indexMatrix] +(matrix.height/5)*4
                while (textCount < item.texts.count()){
                    drawtext(text.toString(), matrix.x[indexMatrix], y, matrix.width, matrix.height / 5 , canvas, 20F, Position.Center)
                    countT += 1
                    break
                }
            }
        }
        Log.d("arrImage" ,countT.toString())
        Log.d("arrText" , countI.toString())
        arrTI.add(countIT(countT, countI))

        return arrTI
    }

    fun drawTextCell(item: Item, matrix: Index, canvas: Canvas, indexMatrix: Int, textCount: Int): Int{
        var count = textCount
        if (textCount >= 0 && textCount < item.texts.count()){
            val text = item.texts[textCount]
            drawtext(text.text.toString(), matrix.x[indexMatrix], matrix.y[indexMatrix], matrix.width, matrix.height , canvas, 25F, Position.Center)
            count = textCount + 1
        }
        return count
    }

    fun drawImageCell(item: Item, matrix: Index , canvas: Canvas, indexMatrix: Int, imgCount: Int): Int{
        var count = imgCount
        if (imgCount >= 0 && imgCount < item.subItems.count()){
            val subItem = item.subItems[count].url
            val x = matrix.x[indexMatrix]
            val y = matrix.y[indexMatrix]
            drawImage(subItem, x, y, matrix.width, matrix.height, canvas , item.imageSolution, item.imageScale)
            count = imgCount + 1
        }
        return count
    }

    fun drawImageHeader(url: String, x: Float, y: Float, width: Float, height: Float, canvas: Canvas , imageSolution: ImageResolution) : Bitmap {

        val pain = Paint().apply {
            style = Paint.Style.FILL
        }
        val imageBefore : Bitmap = ShrinkBitmap(url , width.toInt() , height.toInt())
        if (width <= height){
            val widthnew = width
            var score = imageBefore.height / imageBefore.width
            val heightnew = widthnew * score
            val originY = ( height - heightnew)/2 + y
            val dst = RectF(x, originY, x + widthnew, originY + heightnew)
            canvas.drawBitmap(imageBefore, null, dst, pain)
            return imageBefore
        }else{
            val heightnew = height
            var score = imageBefore.width / imageBefore.height
            val widthnew = heightnew * score
            val originX = (width - widthnew)/2 + x
            val dst = RectF(originX, y, originX + widthnew, y + heightnew)
            canvas.drawBitmap(imageBefore, null, dst, pain)
            return imageBefore
        }
    }

    fun drawLine(startX: Float, startY: Float, stopX: Float, stopY: Float, canvas: Canvas, color: Int){
        val pain = Paint()
        pain.color = color
        pain.strokeWidth = 3F
        canvas.drawLine(startX, startY, stopX, stopY, pain)
    }

    fun drawtext(text: String, x: Float, y: Float, width: Float, height: Float ,canvas: Canvas , textSize: Float, alignment: Position) {
        val pain = Paint(Paint.ANTI_ALIAS_FLAG)
        pain.textSize = textSize
        pain.color = Color.BLACK
        val originY = y + (height - (pain.descent() + pain.ascent())) /2
        var originX : Float
        if (alignment == Position.Center){
            originX = x + (width - pain.measureText(text)) /2
        }else if (alignment == Position.Right){
            originX = x + (width - 20 - pain.measureText(text))
        }else{
            pain.textAlign = Paint.Align.LEFT
            originX = x + 20
        }
        canvas.drawText(text, originX, originY, pain)
    }

    fun drawImage(url: String , x: Float, y: Float, width: Float, height: Float ,canvas: Canvas, imageSolution: ImageResolution, imageScale: ImageScale) : Bitmap {

        val pain = Paint().apply {
            style = Paint.Style.FILL
        }
        val imageBefore : Bitmap = ShrinkBitmap(url , width.toInt()  , height.toInt() )
        if (imageScale == ImageScale.fit)
        {
            if (width <= height){

                val widthnew = width - 10
                var score = imageBefore.height / imageBefore.width
                val heightnew = widthnew * score
                val originY = ( height - heightnew)/2 + y
                val dst = RectF(x + 10, originY, x + widthnew, originY + heightnew)
                Log.d("x" , x.toString())
                Log.d("y" , originY.toString())
                canvas.drawBitmap(imageBefore, null, dst, pain)
                return imageBefore

            }else{

                val heightnew = height
                var score = imageBefore.width / imageBefore.height
                val widthnew = heightnew * score
                val originX = (width - widthnew)/2 + x
                val dst  = RectF(originX, y, originX + widthnew,y + heightnew)
                Log.d("x" , originX.toString())
                Log.d("y" , y.toString())
                canvas.drawBitmap(imageBefore, null, dst, pain)
                return imageBefore
            }
        }else{
            return imageBefore
        }
    }
    fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
        try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) { // if image height already smaller than the required height
                    return source
                }

                val aspectRatio = source.width.toDouble() / source.height.toDouble()
                val targetWidth = (maxLength * aspectRatio).toInt()
                val result = Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
                return result
            } else {
                if (source.width <= maxLength) { // if image width already smaller than the required width
                    return source
                }

                val aspectRatio = source.height.toDouble() / source.width.toDouble()
                val targetHeight = (maxLength * aspectRatio).toInt()

                val result = Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
                return result
            }
        } catch (e: Exception) {
            return source
        }
    }
    fun ShrinkBitmap( photoPath : String , targetW : Int, targetH : Int): Bitmap {

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        var scaleFactor = 1
        if (targetW > 0 || targetH > 0) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH)
        }

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        val bitmap : Bitmap = BitmapFactory.decodeFile(photoPath, bmOptions)
        return bitmap

    }

    fun calcultateCell(item: Item) : Index{
        val spaceHF = 60F
        val layout = item.defaultLayout
        val pageSize = item.pageSize
        val collum = layout.numofColms
        val row = layout.numofRows
        var magin = 20F
        var subHeight = 0F
        if (item.pageHeader.type != PageHeaderType.none){
            if (item.pageFooter.type != PageHeaderType.none){
                subHeight = spaceHF + spaceHF + 15
            }else{
                subHeight = subHeight + spaceHF + magin
            }
        }else if (item.pageFooter.type != PageHeaderType.none){
            subHeight = subHeight + spaceHF + magin
        }else{
            subHeight = magin*2 + 15
        }
        val widthX = (pageSize.width)/collum
        val heightY = (pageSize.height - subHeight)/row

        val arrX = ArrayList<Float>()
        val arrY = ArrayList<Float>()
        for (y in 0..row-1){
            for (x in 0..collum-1){
                if (item.pageHeader.type != PageHeaderType.none){
                    arrX.add(widthX * x)
                    arrY.add(heightY * y + spaceHF + 15)
                }else{
                    arrX.add(widthX * x)
                    arrY.add(heightY * y + magin + 15)
                }
            }
        }
        val index = Index(arrX, arrY, widthX, heightY - 15)
        return index
    }



}