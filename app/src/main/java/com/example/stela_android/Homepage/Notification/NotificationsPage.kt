package com.example.stela_android.Homepage.Notification

import android.app.AlertDialog.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.Petugas.HomepagePrakom
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
        BtnNotif.setOnClickListener {
            makeNotification()
        }
    }

    private fun getNotification(){
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
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
                    rvnotification.adapter = adapter

                    Log.d("Success", "msg: " + response?.body()?.message)
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.d(TAG, "Exception: " + t.message)
            }
        })
    }

    fun makeNotification (){
        val channelID = "CHANNEL_ID_NOTIFICATION"
        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.ic_notifications_page)
            .setContentTitle("Title")
            .setContentText("Some text")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, NotificationsPage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", "somedata")
        val pendingIntent = PendingIntent.getActivity(this,
            0,intent, PendingIntent.FLAG_MUTABLE)
        builder.setContentIntent(pendingIntent)
        val notifManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            var notifChannel : NotificationChannel? =
                notifManager.getNotificationChannel(channelID)
            if (notifChannel == null){
                val importance = NotificationManager.IMPORTANCE_HIGH
                notifChannel = NotificationChannel(channelID, "text", importance)
                notifChannel.setLightColor(Color.GREEN)
                notifChannel.enableVibration(true)
                notifManager.createNotificationChannel(notifChannel)
            }
        }
        notifManager.notify(0, builder.build())


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
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val id_peran = prefs?.getInt("id_peran", 0)
        back_btn.setOnClickListener {
            if (id_peran == 2){
                val i = Intent(this, Homepage::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else if (id_peran == 5){
                val i = Intent(this, HomepagePrakom::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

        }
    }
}