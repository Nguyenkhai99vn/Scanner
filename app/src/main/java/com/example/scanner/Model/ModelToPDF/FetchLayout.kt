package com.example.scanner.Model.ModelToPDF

import com.example.scanner.Model.ModelToPDF.Enum.LayoutType
import com.example.scanner.Model.ModelToPDF.Enum.Orientation

class FetchLayout(var layoutID: Int ,
                  var numColms: Int ,
                  var numRows: Int ,
                  var cell: ArrayList<cellType> ,
                  var orientation: Int ,
                  var layoutType: Int) {

}

class cellType(val cellType: Int) {
}