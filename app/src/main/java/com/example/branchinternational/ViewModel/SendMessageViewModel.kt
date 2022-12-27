package com.example.branchinternational.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.branchinternational.Model.Authentication
import com.example.branchinternational.Model.ClientService
import com.example.branchinternational.Model.Messages
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SendMessageViewModel:ViewModel(){
    val sendmessage = MutableLiveData<Response<Messages>>()
    val sendmessageerror = MutableLiveData<Boolean>()
    private val clientservice = ClientService()
    private val disposable = CompositeDisposable()
    fun refresh(auth:String,threadid:Int,body:String){
        fetchsendmessage(auth,threadid,body)
    }

    private fun fetchsendmessage(auth: String, threadid: Int, body: String) {
        disposable.add(
            clientservice.sendmessage(auth,threadid,body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response<Messages>>(){
                    override fun onSuccess(t: Response<Messages>) {
                        sendmessage.value = t
                    }

                    override fun onError(e: Throwable) {
                        sendmessageerror.value = true
                    }

                })
        )

    }


}
