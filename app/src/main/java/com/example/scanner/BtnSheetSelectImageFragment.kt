package com.example.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Adapter.sheet_camera_img_adapter
import com.example.scanner.Interface.IClickBtnSheetListener
import com.example.scanner.Model.item_image_camera

class DialogSelectImageFragment(var list: List<item_image_camera> , val iClickBtnSheetListener: IClickBtnSheetListener): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val inflate =  inflater.inflate(R.layout.fragment_dialog_select_img, container, false)

        val rccv: RecyclerView = inflate.findViewById(R.id.rccv_sheet2)
        val itemAdapter = sheet_camera_img_adapter(list, object : IClickBtnSheetListener {
            override fun clickItemSheetSelectImage(itemSheet: item_image_camera) {
                iClickBtnSheetListener.clickItemSheetSelectImage(itemSheet)
            }
        })
        rccv.adapter = itemAdapter
        rccv.layoutManager = LinearLayoutManager(context)
        return inflate
    }
}
