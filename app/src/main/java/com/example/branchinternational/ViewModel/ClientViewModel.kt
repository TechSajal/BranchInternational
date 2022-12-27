package com.example.branchinternational.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.branchinternational.Model.Authentication
import com.example.branchinternational.Model.ClientService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ClientViewModel:ViewModel(){
   val auth_tokenlogin = MutableLiveData<Response<Authentication>>()
    val auth_tokenloginerror= MutableLiveData<Boolean>()
    private val clientservice = ClientService()
    private val disposable =CompositeDisposable()
    fun refresh(username:String,password:String){
        fetchAuthtoken(username,password)
    }

    private fun fetchAuthtoken(username: String, password: String) {
       disposable.add(
           clientservice.getAuthToken(username,password)
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(object :DisposableSingleObserver<Response<Authentication>>(){
                   override fun onSuccess(t: Response<Authentication>) {
                       auth_tokenlogin.value = t
                   }

                   override fun onError(e: Throwable) {
                       auth_tokenloginerror.value = true
                   }

               })
       )
    }
}