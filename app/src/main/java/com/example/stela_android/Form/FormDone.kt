package com.example.stela_android.Form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_form_done.*
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_notifications_page.back_btn

class FormDone : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_done)
        val data = intent.extras
        val no_tiket = data?.get("no_tiket")
        kode_tiket.setText(no_tiket.toString())
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