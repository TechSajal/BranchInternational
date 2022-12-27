package com.example.branchinternational.Model

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ClientApi {

    @POST("api/login")
    fun userlogin(@Body request:userpass):Single<Response<Authentication>>

}