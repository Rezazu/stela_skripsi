package com.example.stela_android.Login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.stela_android.R
import com.example.stela_android.Storage.SharedPrefManager

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, Login::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)
    }
}