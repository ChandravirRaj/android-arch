package com.androboy.androidarch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androboy.androidarch.api.APICallHandler
import com.androboy.androidarch.api.APICallManager
import com.androboy.androidarch.api.APIType
import com.androboy.androidarch.api.ApiRequests
import com.androboy.androidarch.model.base.Errors
import com.androboy.androidarch.ui.model.Result
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val apiInterface: ApiRequests) : APICallHandler<Any> {


    private val quoteMutableLiveData = MutableLiveData<List<Result>>()
    private val errorsMutableLiveData = MutableLiveData<Errors>()

    val quoteLiveData: LiveData<List<Result>>
        get() = quoteMutableLiveData

    val errorsLiveData: LiveData<Errors>
        get() = errorsMutableLiveData

    override fun onSuccess(apiType: APIType, response: Any?) {
        Log.d("MMMM", "onSuccess: $apiType")
    }

    override fun onFailure(apiType: APIType, error: Errors) {
        errorsMutableLiveData.postValue(error)
    }

    fun getQuotes(page: Int) {
        val apiCallManager = APICallManager(APIType.LOGIN, this, apiInterface)
        apiCallManager.getQuotes(page)
    }


}