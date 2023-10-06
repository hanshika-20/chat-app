package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context, val messageList: ArrayList<Message>) :RecyclerView.Adapter<ViewHolder>() {
    val item_receive =1
    val item_sent=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==1){
            //inflate receive
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive, parent,false)
            return ReceivedViewHolder(view)
        }
        else{
            //inflate send
            val view:View= LayoutInflater.from(context).inflate(R.layout.sent, parent,false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java){
            //do stuff for sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }
        else{
            //do stuff for receive view holder

            val viewHolder = holder as ReceivedViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return item_sent
        }
        else{
            return item_receive
        }
    }
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage  = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }
    class ReceivedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage  = itemView.findViewById<TextView>(R.id.txt_received_message)
    }

}