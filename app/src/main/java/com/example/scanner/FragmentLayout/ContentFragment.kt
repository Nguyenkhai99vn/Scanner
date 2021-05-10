package com.example.scanner.FragmentLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.scanner.R

class ContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var num = 5
        if(num == 4){
            return inflater.inflate(R.layout.fragment_layout_4, container, false)
        }else if (num == 5) {
            return inflater.inflate(R.layout.fragment_layout_5, container, false)
        }else if (num == 13) {
            return inflater.inflate(R.layout.fragment_layout_13, container, false)
        }else if (num == 14) {
            return inflater.inflate(R.layout.fragment_layout_14, container, false)
        }else{
            return inflater.inflate(R.layout.fragment_layout_4, container, false)
        }
    }

}