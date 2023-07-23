package com.androboy.androidarch.repository

import com.androboy.androidarch.api.ApiRequests
import com.androboy.androidarch.ui.model.QuoteResponse
import retrofit2.Response
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val apiInterface: ApiRequests) {


    suspend fun getQuotes(page: Int) : Response<QuoteResponse> {
        return apiInterface.getQuotes(page)
    }


}