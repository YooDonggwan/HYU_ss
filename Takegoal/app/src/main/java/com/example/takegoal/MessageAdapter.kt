package com.example.takegoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.message_sample.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageViweHolder(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer

class MessageAdapter(var message_array:ArrayList<message_sample>,var click:(MessageAdapter,Int)->Unit):RecyclerView.Adapter<MessageViweHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViweHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.message_sample,parent,false)
        return MessageViweHolder(view)
    }
    override fun getItemCount(): Int {
        return message_array.count()
    }

    override fun onBindViewHolder(holder: MessageViweHolder, position: Int) {

        holder.containerView.messageText.text=message_array[position].text
        holder.containerView.AcceptButton.setOnClickListener {//승인버튼
            println("message"+message_array[position].groupname)
            MainActivity.service.update_user(message_array[position].addresser,message_array[position].groupname,message_array[position].receiver).
                enqueue(object:Callback<Bigdata>{
                override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                }
                override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {
                    click(this@MessageAdapter,position)
                }

            })
        }
        holder.containerView.refuseButton.setOnClickListener {
            MainActivity.service.putMessage(message_array[position].addresser,message_array[position].groupname,message_array[position].receiver).
                enqueue(object:Callback<Bigdata>{
                    override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                    }
                    override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {
                        println(response.body().toString())
                        click(this@MessageAdapter,position)
                    }

                })
        }
    }

}