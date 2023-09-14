package com.example.stela_android.Homepage.Notification

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stela_android.R

class NotificationItem : AppCompatActivity() {
    @Override
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.item_notification)
        val tvKeterangan = findViewById<TextView>(R.id.keterangan)
        val data = intent.getStringExtra("data")
        tvKeterangan.setText(data)
    }
}