package com.example.scanner.Interface

import com.example.scanner.Model.item_image_camera
import com.example.scanner.Model.item_sheet

interface IClickBtnSheetListener {
    fun clickItemSheet(itemSheet: item_sheet){}
    fun clickItemSheetSelectImage(itemSheet: item_image_camera){}
}