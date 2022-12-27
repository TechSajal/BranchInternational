package com.example.branchinternational.View

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.branchinternational.Model.Messages
import com.example.branchinternational.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MessagesListAdapter(val messages:ArrayList<Messages>):RecyclerView.Adapter<MessageViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessage(messa:List<Messages>){
        messages.clear()
        messages.addAll(messa)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_message,parent,false)
        )

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val cur = messages[position]
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val date: Date = dateFormat.parse(cur.timestamp)!! //You will get date object relative to server/client timezone wherever it is parsed
        val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy" + "  HH:mm aaa") //If you need time just put specific format for time like 'HH:mm:ss'
        holder.body.text = cur.body
        holder.userid.text = cur.user_id.toString()
        holder.time.text = formatter.format(date)
    }

    override fun getItemCount():Int{
        return messages.size
    }



}
class   MessageViewHolder(view: View):RecyclerView.ViewHolder(view){
    val body:TextView = view.findViewById(R.id.body_card)
    val userid:TextView = view.findViewById(R.id.userid)
    val time:TextView = view.findViewById(R.id.createdate)
}