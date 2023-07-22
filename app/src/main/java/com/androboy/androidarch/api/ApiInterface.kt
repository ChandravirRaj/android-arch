package com.androboy.androidarch.api

import com.androboy.androidarch.ui.model.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): Response<QuoteResponse>
}