package com.example.stela_android.Homepage.Notification

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Notification.Notification
import com.example.stela_android.Retrofit.Notification.NotificationResponse
import com.example.stela_android.Retrofit.Notification.OnNotifikasiClickListener


class PostAdapter (private val notif: ArrayList<Notification>, private val onNotifikasiClickListener: OnNotifikasiClickListener): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    inner class PostViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun bind(notificationResponse: Notification){
            with(itemView){
                val rlItem: RelativeLayout = findViewById(R.id.item_notification)
                val bellIcon : ImageView = findViewById(R.id.bell)
                val tv_no_tiket : TextView = itemView.findViewById(R.id.no_tiket)
                val tv_keterangan : TextView = itemView.findViewById(R.id.keterangan)
                val tv_tanggal : TextView = itemView.findViewById(R.id.tanggal)
                tv_no_tiket.text = notificationResponse.no_tiket
                tv_keterangan.text = notificationResponse.keterangan
                tv_tanggal.text = notificationResponse.tanggal.toString()

                if (notificationResponse.dibaca == 1){
                    bellIcon.setVisibility(View.GONE);
                } else {
                    bellIcon.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(notif[position])
        holder.itemView.setOnClickListener {
            onNotifikasiClickListener.onNotifikasiItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        Log.d("COUNT",".getItemCountcalled ${notif.size}")
        return notif.size
    }
}