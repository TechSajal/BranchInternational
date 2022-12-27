package com.example.branchinternational.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.branchinternational.Model.Messages
import com.example.branchinternational.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MessagesListAdapter(val context: Context,val messages:ArrayList<Messages>):RecyclerView.Adapter<MessageViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessage(messa:List<Messages>){
        messages.clear()
        messages.addAll(messa)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_message,parent,false)
        )

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val cur = messages[position]
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val date: Date = dateFormat.parse(cur.timestamp)!! //You will get date object relative to server/client timezone wherever it is parsed
        val formatter: DateFormat = SimpleDateFormat("dd.MM.yyyy" + "  HH:mm aaa") //If you need time just put specific format for time like 'HH:mm:ss'
        holder.body.text = cur.body
        if(cur.agent_id == 0){
            holder.useroragenthardtext.text = "UserId:"
            holder.useridoragentid.text = cur.user_id.toString()
        }else{
            holder.useroragenthardtext.text ="Agent:"
            holder.useridoragentid.text = cur.agent_id.toString()
        }
        holder.time.text = formatter.format(date)
        holder.card.setOnClickListener {
            val i = Intent(context,SendingMessageActivity::class.java)
               i.putExtra("thread_id",cur.thread_id)
              context.startActivity(i)

        }
    }

    override fun getItemCount():Int{
        return messages.size
    }



}
class   MessageViewHolder(view: View):RecyclerView.ViewHolder(view){
    val body:TextView = view.findViewById(R.id.body_card)
    val useridoragentid:TextView = view.findViewById(R.id.userid)
    val useroragenthardtext:TextView = view.findViewById(R.id.useridpermtext)
    val time:TextView = view.findViewById(R.id.createdate)
    val card:CardView = view.findViewById(R.id.card_cardview)
}