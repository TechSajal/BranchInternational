package com.example.branchinternational.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.branchinternational.DataStore.Auth_token
import com.example.branchinternational.Model.Messages
import com.example.branchinternational.R
import com.example.branchinternational.ViewModel.MessageViewModel
import com.example.branchinternational.databinding.ActivityMessageBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private lateinit var messageviewmodel:MessageViewModel
    private val messageadapter = MessagesListAdapter(this,arrayListOf())
     lateinit var recyclerView: RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressMessage.isAnimating
        recyclerView = findViewById(R.id.recyclerviewmessage)
        messageviewmodel = ViewModelProviders.of(this)[MessageViewModel::class.java]
        messageviewmodel.refresh(Auth_token.auth_token)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageadapter
        }
        messageviewmodel.messages.observe(this, Observer { message ->
            message?.let { it ->
                var saj : ArrayList<Messages> = ArrayList()
                var equallist:ArrayList<Messages> = ArrayList()
                 for(threadid in 1..56){
                    for (item in it){
                        if (item.thread_id == threadid){
                            equallist.add(item)
                        }
                    }

                     equallist.sortWith(compareBy {it.timestamp })
                     if (equallist.size >0){
                         saj.add(equallist[equallist.size-1])
                     }
                     equallist.clear()
                }
                binding.progressMessage.visibility = View.GONE
                messageadapter.updateMessage(saj)

            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }
}