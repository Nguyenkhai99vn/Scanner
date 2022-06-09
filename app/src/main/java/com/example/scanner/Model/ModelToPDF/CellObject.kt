package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.CellType
import io.realm.RealmObject

open class CellObject: RealmObject() {
     var header: HeaderObject? = null
     var footer: HeaderObject? = null
     var text: TextObject?  = null
     var imageURL : String? = null
    var cellType = 0
//    var perCent = 100

    fun getObject() : Cell {
        var Cell =Cell()

        Cell.header = header?.getObject()
        Cell.footer = footer?.getObject()
        Cell.text = text?.getObject()
        Cell.imageURL = imageURL
        Cell.cellType = CellType.values()[cellType]

        return Cell
    }

    fun setRealmObject(cell: Cell){
        header = cell.header?.toRealmObject()
        footer = cell.footer?.toRealmObject()
        text = cell.text?.toRealmObject()
        imageURL = cell.imageURL
        cellType = cell.cellType.ordinal
    }
}