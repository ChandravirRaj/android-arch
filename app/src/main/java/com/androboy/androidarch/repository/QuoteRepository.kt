package com.androboy.androidarch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androboy.androidarch.api.ApiRequests
import com.androboy.androidarch.ui.model.QuoteResponse
import com.androboy.androidarch.utils.NetworkResult
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val apiInterface: ApiRequests) {

    private val _quoteLiveData = MutableLiveData<NetworkResult<QuoteResponse>>()
    val quoteLiveData: LiveData<NetworkResult<QuoteResponse>>
        get() = _quoteLiveData

    suspend fun getQuotes(page: Int) {
        val response = apiInterface.getQuotes(page)
        if (response.isSuccessful && response.body() != null && response.code() == 200) {
            _quoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _quoteLiveData.postValue(NetworkResult.Error("Getting some errors", response.body()!!))
        } else {
            _quoteLiveData.postValue(NetworkResult.Error("Getting some errors", response.body()!!))
        }

//        apiInterface.getQuotes(page).onSuccess {
//            _quoteLiveData.postValue(NetworkResult.Success(it))
//        }.onFailure {
//            _quoteLiveData.postValue(NetworkResult.Error(it.message, response.body()!!))
//        }
    }


}