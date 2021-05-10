package com.example.scanner.Model.ModelToPDF

import android.os.Parcel
import android.os.Parcelable
import com.example.scanner.Model.ModelToPDF.Enum.LayoutType
import com.example.scanner.Model.ModelToPDF.Enum.Margin
import com.example.scanner.Model.ModelToPDF.Enum.Orientation
import io.realm.RealmList
import java.util.ArrayList

class Layout(var layoutID : Int = 1,
             var numofColms: Int = 1,
             var numofRows: Int = 1,
             var magin : Margin = Margin.Normal,
             var cell : ArrayList<Cell>?,
             var header : PageHeader? = null,
             var footer : PageHeader? =null,
             var orientation: Orientation = Orientation.portrait,
             var layoutType : LayoutType = LayoutType.pageBasic
) {
//    var layoutID : Int  = 1
//    var numofColms: Int = 1
//    var numofRows: Int = 1
//    var magin: Margin = Margin.Normal
//    var cell = ArrayList<Cell> ()
////    Does not use in this version
//    var header: PageHeader? = null
//    var footer: PageHeader? = null
//    var orientation: Orientation = Orientation.portrait
//    var layoutType : LayoutType = LayoutType.pageBasic


}

