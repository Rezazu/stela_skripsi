package com.example.stela_android.Homepage.Notification

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Notification.Notification
import com.example.stela_android.Retrofit.Notification.NotificationApi
import com.example.stela_android.Retrofit.Notification.NotificationResponse
import com.example.stela_android.Retrofit.Retrofit
import kotlinx.android.synthetic.main.activity_notifications_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsPage : AppCompatActivity() {

    private val notif = ArrayList<Notification>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_page)
        backBtnListener()
        getNotification()
        recycleviewBuilder()
    }

    private fun getNotification(){
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        Log.d("test", "test:"+token)
        val retro = Retrofit.getRetroData(token!!).create(NotificationApi::class.java)
        retro.getNotifikasi().enqueue(object: Callback<NotificationResponse> {
            override fun onResponse(
                call: Call<NotificationResponse>,
                response: Response<NotificationResponse>
            ) {
                response.body()?.data?.let { notif.addAll(it) }
                rvnotification.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PostAdapter(notif)

                    val notifAdapter = adapter
                    rvnotification.adapter = adapter

                    Log.d("Success", "msg: " + response?.body()?.message)
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.d(TAG, "Exception: " + t.message)
            }
        })
    }

    private fun recycleviewBuilder(){
        val rvNotification = findViewById<View>(R.id.rvnotification) as RecyclerView

        rvnotification.setHasFixedSize(true)
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvNotification.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = PostAdapter(notif)
        rvNotification.adapter = adapter
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, Homepage::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}