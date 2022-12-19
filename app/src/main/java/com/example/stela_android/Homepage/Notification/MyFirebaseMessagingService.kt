package com.example.stela_android.Homepage.Notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMessagingServ"

    @Inject
    lateinit var mNotificationManager: MyNotificationManager

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d("MyFirebaseIIDService", "Refreshed token: $s")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Data Payload: " + remoteMessage.data)
            try {

                val no_tiket = remoteMessage.data["no_tiket"]
                val keterangan = remoteMessage.data["keterangan"]

                mNotificationManager.textNotification(no_tiket, keterangan)

            } catch (e: Exception) {
                Log.d(TAG, "Exception: " + e.message)
            }
        }

    }
}

