package com.androboy.androidarch.api

import com.androboy.androidarch.model.base.Errors

/**
 * This is generic interface for handling api response
 * @param T generic object
 * */
interface APICallHandler<T> {

    /**
     * This method is calls when any api response come with status code 200
     * @param apiType is type of APIType to define api type
     * @param response is type T generic
     * */
    fun onSuccess(apiType: APIType, response: T?)

    /**
     * This method is calls when any api response come with failure
     * @param apiType is type of APIType to define api type
     * @param error is type Errors
     * */
    fun onFailure(apiType: APIType, error: Errors)
}