package com.example.stela_android.Form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_notifications_page.*

class FormDone : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_done)

        backBtnListener()


    }
    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }
}