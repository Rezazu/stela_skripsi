package com.example.stela_android.Form

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_form_done.*
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_notifications_page.back_btn

class FormDone : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_done)
        val no_tiket = intent.getStringExtra("no_tiket").toString()
        val tv_no_tiket : TextView = findViewById(R.id.tv_no_tiket) as TextView
        tv_no_tiket.setText(no_tiket)
        backBtnListener()
    }
    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, Homepage::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}