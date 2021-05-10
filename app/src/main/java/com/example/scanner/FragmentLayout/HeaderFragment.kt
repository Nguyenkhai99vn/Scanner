package com.example.scanner.FragmentLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.scanner.R

class HeaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var num = 2
        if(num == 2){
            return inflater.inflate(R.layout.fragment_header_2, container, false)
        }else {
            return inflater.inflate(R.layout.fragment_header_1, container, false)
        }

    }

}