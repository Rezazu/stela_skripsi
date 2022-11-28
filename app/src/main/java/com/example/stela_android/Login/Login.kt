package com.example.stela_android.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.stela_android.Homepage.HomepagePrakom
import com.example.stela_android.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_masuk = findViewById<ImageButton>(R.id.btn_masuk)
        btn_masuk.setOnClickListener{
            val intent_masuk = Intent(this, HomepagePrakom::class.java)
            startActivity(intent_masuk)
        }
    }
}