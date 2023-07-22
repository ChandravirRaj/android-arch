package com.androboy.androidarch.repository

import com.androboy.androidarch.api.ApiInterface
import com.androboy.androidarch.ui.model.QuoteResponse
import retrofit2.Response
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getQuotes(page: Int): Response<QuoteResponse> {
        return apiInterface.getQuotes(page)
    }
}