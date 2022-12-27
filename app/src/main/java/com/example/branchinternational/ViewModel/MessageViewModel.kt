package com.example.branchinternational.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.branchinternational.Model.ClientService
import com.example.branchinternational.Model.Messages
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MessageViewModel:ViewModel(){
    val messages = MutableLiveData<List<Messages>>()
    val messageerror = MutableLiveData<Boolean>()
    private val clientservice = ClientService()
    private val disposable = CompositeDisposable()

    fun refresh(auth:String){
        fetchmessages(auth)
    }

    private fun fetchmessages(auth: String) {
        disposable.add(
            clientservice.getmessages(auth)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Messages>>(),SingleObserver<List<Messages>>,Disposable{
                    override fun onSuccess(t: List<Messages>) {
                        messages.value = t
                    }

                    override fun onError(e: Throwable) {
                        messageerror.value = true
                    }

                })
        )
    }
}