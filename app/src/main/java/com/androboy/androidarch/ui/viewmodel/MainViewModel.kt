package com.androboy.androidarch.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androboy.androidarch.db.entities.User
import com.androboy.androidarch.model.base.Errors
import com.androboy.androidarch.repository.QuoteRepository
import com.androboy.androidarch.repository.UserRepository
import com.androboy.androidarch.ui.model.Quote
import com.androboy.androidarch.ui.model.QuoteRes
import com.androboy.androidarch.ui.model.Result
import com.androboy.androidarch.ui.model.Student
import com.androboy.androidarch.utils.GsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val userRepository: UserRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {


    private val _quoteLiveData = MutableLiveData<List<Result>> ()
    private val _errorsLiveData = MutableLiveData<Errors> ()


    val quoteLiveData: LiveData<List<Result>>
        get() = _quoteLiveData


    val errorsLiveData: LiveData<Errors>
        get() = _errorsLiveData

//    val errorsLiveData: LiveData<Errors>
//        get() = quoteRepository.errorsLiveData



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




    fun getQuotes(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = quoteRepository.getQuotes(page)
            if (result.body() != null) {
                val response = result.body()!!.results
                _quoteLiveData.postValue(response)
            }else{
                val errors = Errors()
                errors.message = "Something Went Wrong"
                _errorsLiveData.postValue(errors)
            }
        }
//        viewModelScope.launch(Dispatchers.IO) {
//            quoteRepository.getQuotes(page)
//        }

//        quoteRepository.getQuotes(page)


    }

}