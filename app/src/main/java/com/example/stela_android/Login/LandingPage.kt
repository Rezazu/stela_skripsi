package com.example.stela_android.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stela_android.R

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