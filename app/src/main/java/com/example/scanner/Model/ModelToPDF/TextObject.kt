package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.Position
import io.realm.RealmObject

open class TextObject : RealmObject() {
    var pageID = 1
    var possition = 1 // Possition in the PDF file
    lateinit var text : String
    lateinit var textColor : String
    var textAlignment : Int = 1 // 2,3
    lateinit var other : String // Do not use at this time

    fun getObject() : Text {
        val Text = Text()
        Text.text = text
        Text.pageID = pageID
        Text.textColor =textColor
        Text.textAlignment = Position.values()[textAlignment]
        Text.other = other
        return text as Text
    }

    fun setReamlObject(textObj : Text?){
        pageID = textObj!!.pageID
        text = textObj.text.toString()
        textColor =textObj.textColor.toString()
        textAlignment = textObj.textAlignment.ordinal
        other = textObj.other.toString()

    }

}
