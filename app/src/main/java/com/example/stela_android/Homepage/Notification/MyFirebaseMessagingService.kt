package com.example.stela_android.Homepage.Notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.internal.notify
import kotlin.random.Random

const val channelId = "notification_channel"
const val channelName = "package com.example.stela_android.Homepage.Notification"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {


//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        if(remoteMessage.getNotification() != null){
//            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
//        }
//
//    }
//    //GenerateNotification
//    fun generateNotification(no_tiket: String, keterangan: String){
//        val intent = Intent(this, Homepage::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.drawable.stela_logo_small)
//            .setAutoCancel(true)
//            .setVibrate(longArrayOf(1000,1000))
//            .setOnlyAlertOnce(true)
//            .setContentIntent(pendingIntent)
//
//        builder = builder.setContent(getRemoteView(no_tiket, keterangan))
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//
//        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(notificationChannel)
//
//
//        notificationManager.notify(0, builder.build())
//
//    }
//
//    fun getRemoteView(no_tiket: String, keterangan: String): RemoteViews{
//        val remoteView = RemoteViews("package com.example.stela_android.Homepage.Notification", R.layout.notification)
//
//        remoteView.setTextViewText(R.id.no_tiket, no_tiket)
//        remoteView.setTextViewText(R.id.keterangan, keterangan)
//        remoteView.setImageViewResource(R.id.logo, R.drawable.stela_logo_small)
//
//        return remoteView
//
//    }

    //Attach the Notif with custom layout
    //Show notif

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this, Homepage::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
        createNotificationChannel(manager as NotificationManager)

        val pendingIntent = PendingIntent.getActivities(this, 0,
        arrayOf(intent), PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.data["no_tiket"])
            .setContentText(message.data["keterangan"])
            .setSmallIcon(R.drawable.stela_logo_small)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(Random.nextInt(),notification)

    }

    private fun createNotificationChannel(manager: NotificationManager){
        val channel = NotificationChannel(channelId,"notifikasi",
        NotificationManager.IMPORTANCE_HIGH)

        channel.description = "Notifikasi"
        channel.enableLights(true)


        manager.createNotificationChannel(channel)
    }



}