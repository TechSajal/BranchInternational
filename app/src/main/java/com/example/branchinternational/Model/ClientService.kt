package com.example.branchinternational.Model

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ClientService {
    private val  BASE_URL = "https://android-messaging.branch.co"
    private val  api:ClientApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ClientApi::class.java)

    fun getAuthToken(username:String,password:String):Single<Response<Authentication>>{
        return api.userlogin(userpass(username,password))
    }

    fun getmessages(auth:String):Single<List<Messages>>{
        return api.getmessage(auth)
    }

    fun sendmessage(auth: String,threadid: Int,body: String):Single<Response<Messages>>{
        return api.sendmessage(auth,sendclientmessage(threadid,body))
    }
}