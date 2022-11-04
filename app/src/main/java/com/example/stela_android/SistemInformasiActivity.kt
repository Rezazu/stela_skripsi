package com.example.stela_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notifications_page.*

class SistemInformasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sistem_informasi)

        backBtnListener()
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }
}