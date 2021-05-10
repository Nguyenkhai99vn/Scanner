package com.example.scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.Adapter.layout_adapter
import com.example.scanner.Interface.IClickItemLayoutListener
import com.example.scanner.Model.item_layout
import kotlinx.android.synthetic.main.fragment_list_layout.*

class list_Layout : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrlayout1 = ArrayList<item_layout>()
        val arrlayout2 = ArrayList<item_layout>()
        val arrlayout3 = ArrayList<item_layout>()

        arrlayout1.add(item_layout(R.drawable.mot , R.drawable.done))
        arrlayout1.add(item_layout(R.drawable.hai , R.drawable.done))
        arrlayout1.add(item_layout(R.drawable.ba , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.bon , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.nam , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.sau , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.bay , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.tam , R.drawable.done))
        arrlayout2.add(item_layout(R.drawable.chin , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.muoi , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mmot , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mhai , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mba , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mbon , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mlam , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.msau , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mbay , R.drawable.done))
        arrlayout3.add(item_layout(R.drawable.mtam , R.drawable.done))

        rccv_1.adapter = layout_adapter(arrlayout1)
        rccv_1.layoutManager = GridLayoutManager(context , 3)
        rccv_2.adapter = layout_adapter(arrlayout2)
        rccv_2.layoutManager = GridLayoutManager(context , 3)
        rccv_3.adapter = layout_adapter(arrlayout3)
        rccv_3.layoutManager = GridLayoutManager(context , 3)
    }

}

