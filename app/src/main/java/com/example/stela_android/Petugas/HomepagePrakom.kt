package com.example.stela_android.Petugas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.stela_android.Homepage.Notification.NotificationsPage
import com.example.stela_android.Login.Login
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Notification.Notification
import com.example.stela_android.Retrofit.Notification.NotificationApi
import com.example.stela_android.Retrofit.Notification.NotificationResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_homepage.bottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class HomepagePrakom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage_prakom)
        ticketActiveBtnHandler()
        val second_in_milli = 15000
        val timerHandler = Handler()
        val timerRunnable = object : Runnable{
            override fun run() {
                getNewNotif()
                timerHandler.postDelayed(this, second_in_milli.toLong())
            }
        }
        timerHandler.postDelayed(timerRunnable,second_in_milli.toLong())
    }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    fun ticketActiveBtnHandler() {
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.itemIconTintList = null

        val profile = ProfilePetugas()
        val home = HomePrakom()

        setCurrentFragment(home)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btn_profile -> setCurrentFragment(profile)
                R.id.btn_home -> setCurrentFragment(home)
            }
            true
        }
    }

    fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

    fun makeNotification (title:String, text:String){
        val channelID = "CHANNEL_ID_NOTIFICATION"
        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.stelalogo)
            .setContentTitle(title)
            .setContentText(text)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)

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

    fun getNewNotif() {
        val notif = ArrayList<Notification>()
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
                fun takeNotif (notificationResponse: Notification){
                    val no = notificationResponse.no_tiket.toString()
                    val ket = notificationResponse.keterangan.toString()
                    val tgl = notificationResponse.tanggal
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val date = sdf.parse(tgl)
                    val notifStamp = java.sql.Timestamp(date.time)
                    val currentStamp = Timestamp(System.currentTimeMillis() - 15*1000)
                    if (notifStamp >= currentStamp) {
                        makeNotification(ket, no)
                    }
                }
                notif.firstOrNull()?.let { takeNotif(it) }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "Exception: " + t.message)
            }
        })
    }
}