package com.example.scanner.Interface

import com.example.scanner.Model.ModelToPDF.Layout


interface layoutCallBack {
    fun onSuccess(layout: Layout)

    fun onError(throwable: Throwable)
}