package com.example.stela_android.Homepage.Notification

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Retrofit.Notification.Notification
import com.example.stela_android.Retrofit.Notification.NotificationResponse

class NotifAdapter (private val notif: ArrayList<Notification>)  {
    inner class PostViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        fun bind (notificationResponse: Notification) {
            with(itemView){
                val no_tiket = notificationResponse.no_tiket
                val ket_tiket = notificationResponse.keterangan
            }
        }
    }

    fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        holder.bind(notif[position])
    }

}