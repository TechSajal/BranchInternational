package com.example.branchinternational.View


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.branchinternational.DataStore.Auth_token
import com.example.branchinternational.Model.Authentication
import com.example.branchinternational.Model.ClientService
import com.example.branchinternational.Model.userpass
import com.example.branchinternational.ViewModel.ClientViewModel
import com.example.branchinternational.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var clientviewmodel:ClientViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           binding = ActivityLoginBinding.inflate(layoutInflater)
           setContentView(binding.root)
           clientviewmodel = ViewModelProviders.of(this)[ClientViewModel::class.java]
           binding.btnLogin.setOnClickListener {
               if (binding.edUsername.text.isNullOrEmpty()){
                   binding.edUsernametextfield.helperText = "Required"
               }else{
                   binding.edUsernametextfield.helperText = null
               }
               if(binding.edPassword.text.isNullOrEmpty()){
                   binding.edPasswordtextfield.helperText = "Required"
               }else{
                   binding.edPasswordtextfield.helperText = null
               }
               if (binding.edUsernametextfield.helperText == null && binding.edPasswordtextfield.helperText == null){
                   if (binding.edUsername.text.toString().contentEquals(binding.edPassword.text!!.reversed().toString())){
                       clientviewmodel.refresh(binding.edUsername.text.toString(),binding.edPassword.text.toString())
                       clientviewmodel.auth_tokenlogin.observe(this){ auth ->
                           if (auth.isSuccessful) {
                              val i = Intent(this,MessageActivity::class.java)
                               startActivity(i)
                           }
                       }
                       clientviewmodel.auth_tokenloginerror.observe(this){iserror ->
                           iserror?.let {
                               if (it) {
                                   Toast.makeText(this, "error sajal", Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                   }else{
                       Toast.makeText(this,"Plz Check Crendentials",Toast.LENGTH_SHORT).show()
                   }

               }



       }
    }
}