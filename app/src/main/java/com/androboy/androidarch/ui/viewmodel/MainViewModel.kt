package com.androboy.androidarch.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.androboy.androidarch.ui.model.Quote
import com.androboy.androidarch.utils.GsonUtils
import java.io.InputStream


class MainViewModel(context: Context) : ViewModel() {
    var quoteList:Array<Quote> = emptyArray()


    init {
        quoteList = readFile(context)
    }


    fun readFile(context: Context): Array<Quote>{
        val inputStream : InputStream = context.assets.open("quote")
        val size:Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer)
        return GsonUtils.parseJson(json,Array<Quote>::class.java)

    }

}