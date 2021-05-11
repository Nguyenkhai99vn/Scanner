package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.PageHeaderType
import com.example.scanner.Model.ModelToPDF.Enum.Position
import io.realm.RealmObject

open class HeaderObject : RealmObject() {
    var type = 0
    var text : TextObject? = null
    var imageURL: String? = null
    var alignment = 0

    fun getObject() : PageHeader {
        var pageHeader = PageHeader()
        pageHeader.type = PageHeaderType.values()[type]
        pageHeader.imageURL = imageURL
        pageHeader.text = text?.getObject()!!
        pageHeader.alignment = Position.values()[alignment]
        return pageHeader
    }

    fun setRealmObject(header: PageHeader){
        type = header.type.ordinal
        text = header.text.toRealmObject()
        imageURL = header.imageURL
        alignment =header.alignment.ordinal
    }
}