package com.example.scanner.Model.ModelToPDF

import android.text.Editable
import com.example.scanner.Model.ModelToPDF.Enum.Position

class Text(
        var pageID: Int = 1,
        var text: String? = null,
        var textColor: String? = null,
        var textAlignment: Position = Position.Left,
        var other: String? = null,
        var idxInList: Int = 0
)  {

//        var pageID: Int = 1
//        var text: String? = null
//        var textColor: String? = null
//        var textAlignment: Position = Position.Left
//        var other: String? = null
//        var idxInList: Int = 0

    fun toRealmObject() : TextObject {
        val obj = TextObject()
        obj.setReamlObject(this)
        return obj
    }

}