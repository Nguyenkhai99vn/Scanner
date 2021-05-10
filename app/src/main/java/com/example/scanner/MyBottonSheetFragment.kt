package com.example.scanner

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.Adapter.sheet_adapter
import com.example.scanner.Interface.IClickBtnSheetListener
import com.example.scanner.Model.item_sheet
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottonSheetFragment(var list: List<item_sheet>, val iClickBtnSheetListener: IClickBtnSheetListener): BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bottonSheetDialog: BottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_bottonsheet,null)
        bottonSheetDialog.setContentView(view)
        val rccv : RecyclerView = view.findViewById(R.id.rccv_sheet1)
        val itemAdapter = sheet_adapter(list , object : IClickBtnSheetListener {
            override fun clickItemSheet(itemSheet: item_sheet) {
                iClickBtnSheetListener.clickItemSheet(itemSheet)
            }
        })
        rccv.adapter = itemAdapter
        rccv.layoutManager = LinearLayoutManager(context)
        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rccv.addItemDecoration(itemDecoration)
        return bottonSheetDialog
    }
}