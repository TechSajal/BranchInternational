package com.example.branchinternational.View

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.branchinternational.DataStore.Auth_token
import com.example.branchinternational.Model.Messages
import com.example.branchinternational.R
import com.example.branchinternational.ViewModel.MessageViewModel
import com.example.branchinternational.ViewModel.SendMessageViewModel
import com.example.branchinternational.databinding.ActivitySendingMessageBinding

class SendingMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendingMessageBinding
     private var thread_id : Int = -1
     private lateinit var messageviewmodel: MessageViewModel
     private lateinit var sendmessageviewmodel:SendMessageViewModel
     private val messageadapter = MessagesListAdapter(this,arrayListOf())
     lateinit var recyclerView: RecyclerView
     @SuppressLint("NotifyDataSetChanged")
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivitySendingMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
         binding.progressbar.isAnimating
         binding.bottonSendandresolve.visibility = View.GONE
         binding.edreply.visibility = View.GONE
         binding.replttextfield.visibility = View.GONE
        thread_id = intent.getIntExtra("thread_id",0)
         sendmessageviewmodel = ViewModelProviders.of(this)[SendMessageViewModel::class.java]
        messageviewmodel = ViewModelProviders.of(this)[MessageViewModel::class.java]
        recyclerView = findViewById(R.id.recyclerviewmessage_sending)
        messageviewmodel.refresh(Auth_token.auth_token)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageadapter
        }
        messageviewmodel.messages.observe(this,Observer{message ->
            message?.let{it ->
                val listbythreadid:ArrayList<Messages> = ArrayList()
                val list:ArrayList<Messages> = ArrayList()
                for (item in it){
                    if (item.thread_id == thread_id){
                        listbythreadid.add(item)
                    }
                }
                listbythreadid.sortWith(compareBy { it.timestamp })
                if (listbythreadid.size>0){
                    list.addAll(listbythreadid)
                }
                binding.bottonSendandresolve.visibility = View.VISIBLE
                binding.edreply.visibility = View.VISIBLE
                binding.replttextfield.visibility = View.VISIBLE
                binding.progressbar.visibility = View.GONE
                messageadapter.updateMessage(list)
            }
        })

         binding.bottonSendandresolve.setOnClickListener {
             binding.progressbar.visibility = View.VISIBLE
             if (binding.edreply.text.isNullOrEmpty()){
                 binding.replttextfield.helperText = "Required"
             }else{
                 binding.replttextfield.helperText = null
             }
             if (binding.replttextfield.helperText == null){
                 sendmessageviewmodel.refresh(Auth_token.auth_token,thread_id,binding.edreply.text.toString())
                 sendmessageviewmodel.sendmessage.observe(this, Observer { send->
                     send?.let { it ->
                         if (it.isSuccessful){
                             binding.progressbar.visibility = View.GONE
                             finish()
                             startActivity(intent)
                         }else{
                             Toast.makeText(this,"Plz Try Again Later",Toast.LENGTH_SHORT).show()
                         }
                     }
                 })
             }
         }

    }
}