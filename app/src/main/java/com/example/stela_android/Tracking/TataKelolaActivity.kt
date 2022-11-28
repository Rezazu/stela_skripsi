package com.example.stela_android.Tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_notifications_page.*

class TataKelolaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tata_kelola)

        backBtnListener()
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }
}