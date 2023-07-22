package com.androboy.androidarch.api

import com.androboy.androidarch.ArchApp
import com.androboy.androidarch.R
import com.androboy.androidarch.model.base.BaseResponse
import com.androboy.androidarch.model.base.Errors
import com.androboy.androidarch.ui.model.QuoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * APICallManager<T>(mCallType: APIType, @mAPICallHandler: APICallHandler<T>) : it handles all api call overall application
 * @param mCallType is type APIType
 * @param mAPICallHandler is type APICallHandler<T>
 * it extends Callback<BaseResponse<T>> interface
 * */
class APICallManager<Any>(
    private val mCallType: APIType,
    private val mAPICallHandler: APICallHandler<Any>,
    private val apiInterface: ApiRequests
) : Callback<BaseResponse<Any>> {

    /**
     * onResponse():
     * @param call is type Call<BaseResponse<T>>?
     * @param response is type Response<BaseResponse<T>>
     * */
    override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
        if (response.code() == APIStatusCode.OK || response.code() == APIStatusCode.CREATED || response.code() == APIStatusCode.NO_CONTENT) {
            val body = response.body()
            if (body != null) {
                if (body.statusCode == 1 && body.data != null) {

                    mAPICallHandler.onSuccess(mCallType, body.data)

                } else {
                    if (body.error != null) {
                        //  if (body.error?.errorCode == 17 || body.error?.errorCode== APIStatusCode.CREATED) {
                        if (body.error?.errorCode == 17) {
//                            PreferenceKeeper.getInstance().isLogin = false
//                            PreferenceKeeper.getInstance().accessToken = null
//                            val intent = Intent(App.INSTANCE, SignInActivity::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                            ArchApp.INSTANCE.startActivity(intent)

                        } else {
                            mAPICallHandler.onFailure(mCallType, body.error!!)
                        }
                    } else {
                        val errors = Errors()
                        val errorMessage = ArchApp.INSTANCE.getString(R.string.something_went_wrong)
                        errors.message = errorMessage
                        mAPICallHandler.onFailure(mCallType, errors)
                    }
                }
            }
        } else {
            val errors = Errors()
            val errorMessage =
                ArchApp.INSTANCE.getString(R.string.something_went_wrong) //"Something went wrong"
            errors.message = errorMessage
            mAPICallHandler.onFailure(mCallType, errors)
        }
    }

    /**
     * onFailure(): it call when any error comes in api response
     * @param call  is type Call<BaseResponse<T>>?
     * @param throwable is type Throwable
     *
     * */

    override fun onFailure(call: Call<BaseResponse<Any>>, throwable: Throwable) {
        val errorCode = 0
        val message: String? =
            if (throwable is UnknownHostException || throwable is SocketException || throwable is SocketTimeoutException) {
                ArchApp.INSTANCE.resources.getString(R.string.something_went_wrong)
            } else {
                throwable.message
            }

        val errors = Errors()
        errors.message = message
        mAPICallHandler.onFailure(mCallType, errors)
    }



    fun getQuotes(page:Int) {
        apiInterface.getQuotes(page)
            .enqueue(this@APICallManager as Callback<BaseResponse<QuoteResponse>>)
    }

    /**
     * signUpAPI () : for signup flow
     * @param email
     * @param password
     * @param country_code
     * @param mobile
     * @param name
     * */
//    fun signUpAPI(
//        email: String?,
//        password: String?,
//        country_code: String?,
//        mobile: String?,
//        name: String?
//    ) {
//
//        APIClient.getClient()
//            .signUpAPI(email, password, country_code, mobile, name)
//            .enqueue(this@APICallManager as Callback<BaseResponse<UserProfileResponse?>>)
//    }

}
