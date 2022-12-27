package com.example.branchinternational.Model

import androidx.lifecycle.MutableLiveData
import com.example.branchinternational.DataStore.Auth_token
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ClientApi {

    @POST("api/login")
    fun userlogin(@Body request:userpass):Single<Response<Authentication>>


    @GET("api/messages")
    fun getmessage(@Header("X-Branch-Auth-Token")auth:String):Single<List<Messages>>

    @POST("api/messages")
    fun sendmessage(@Header("X-Branch-Auth-Token")auth: String,@Body request:sendclientmessage):Single<Response<Messages>>
}