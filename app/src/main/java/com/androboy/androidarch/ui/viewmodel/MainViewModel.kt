package com.androboy.androidarch.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androboy.androidarch.ui.model.Quote
import com.androboy.androidarch.ui.model.QuoteRes
import com.androboy.androidarch.ui.model.Student
import com.androboy.androidarch.utils.GsonUtils
import java.io.InputStream


class MainViewModel(context: Context) : ViewModel() {
    private val studentList = ArrayList<Student>()
    private var studentMutableLiveData = MutableLiveData<ArrayList<Student>>()
    val studentLiveData: LiveData<ArrayList<Student>>
        get() = studentMutableLiveData

    private var quoteList: Array<Quote> = emptyArray()
    private var index: Int = 0


    init {
        quoteList = readFile(context)
    }

    private fun readFile(context: Context): Array<Quote> {
        val inputStream: InputStream = context.assets.open("quote.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer)
        val res: QuoteRes = GsonUtils.parseJson(json, QuoteRes::class.java)
        return res.quotes
    }



    fun getQuote(): Quote {
        return quoteList[0]
    }

    fun nextQuote(): Quote {
        if (index < quoteList.size)
            index++
        else index

        return quoteList[index]
    }

    fun prevQuote(): Quote {
        index--
        if (index > 0)
            index--
        else index

        return quoteList[index]
    }

    fun updateStudentList(student: Student){
        studentList.add(student);
        studentMutableLiveData.value = studentList
    }

}