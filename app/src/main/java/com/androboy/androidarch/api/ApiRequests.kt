package com.androboy.androidarch.api

import com.androboy.androidarch.model.base.BaseResponse
import com.androboy.androidarch.ui.model.QuoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {
    @GET("/quotes")
    fun getQuotes(@Query("page") page: Int): Call<BaseResponse<QuoteResponse>>
}