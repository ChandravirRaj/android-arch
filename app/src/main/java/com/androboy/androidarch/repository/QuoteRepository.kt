package com.androboy.androidarch.repository

import com.androboy.androidarch.api.ApiRequests
import com.androboy.androidarch.ui.model.QuoteResponse
import com.androboy.androidarch.utils.NetworkResult
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val apiInterface: ApiRequests) : BaseRepo() {

    suspend fun getQuotes(page: Int): NetworkResult<QuoteResponse> {
        return safeApiCall { apiInterface.getQuotes(page) }
    }


}