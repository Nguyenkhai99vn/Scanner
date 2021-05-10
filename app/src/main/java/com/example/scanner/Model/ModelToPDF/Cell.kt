package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.CellType

class Cell(
        var header: PageHeader? = null, // header = nil -> have no header
        var footer: PageHeader? = null, // footer = nil -> have no footer
        var text: Text? = null, // Text = nil -> It's image
        var imageURL: String? = null,
        var cellType: CellType = CellType.none
) {

//    var header: PageHeader? = null // header = nil -> have no header
//    var footer: PageHeader? = null // footer = nil -> have no footer
//    var text: Text? = null // Text = nil -> It's image
//    var imageURL: String? = null
//    var cellType: CellType = CellType.none
//    var perCent: Int = 100 bỏ qua
    @JvmName("getCellType1")
    fun getCellType() : CellType {
        if (text != null) {
            return CellType.text
        }
        if (imageURL != null && header == null && footer == null) {
            return CellType.image
        }
        if (imageURL != null && header != null && footer == null) {
            return CellType.image_header
        }
        if (imageURL != null && header == null && footer != null) {
            return CellType.image_footer
        }
        if (imageURL != null && header != null && footer != null) {
            return CellType.image_header_footer
        }else {
            return CellType.none
        }
    }
    fun toRealmObject() : CellObject {
        val cellObj = CellObject()
        cellObj.setRealmObject(this)
        return cellObj
    }

    fun getObject() : Cell {
        return this
    }

    fun remove() {
        if (text != null){
            text = null
        }
        if (imageURL != null){

            imageURL = null
        }
    }

}
