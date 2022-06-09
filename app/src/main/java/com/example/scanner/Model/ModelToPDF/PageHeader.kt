package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.PageHeaderType
import com.example.scanner.Model.ModelToPDF.Enum.Position

class PageHeader(
        var type: PageHeaderType = PageHeaderType.none,
        var text: Text = Text(),
        var imageURL : String? = null,
        var alignment : Position = Position.Left
) {

//    var type: PageHeaderType = PageHeaderType.none
//    var text = Text()
//    var imageURL : String? = null
//    var alignment : Position = Position.Left

    fun toRealmObject() : HeaderObject {
        val obj = HeaderObject()

        obj.type = type.ordinal
        obj.text = text.toRealmObject()
        obj.imageURL = imageURL
        obj.alignment = alignment.ordinal

        return obj
    }

}