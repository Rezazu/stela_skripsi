package com.example.stela_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        ticketActiveBtnHandler()
    }

    fun ticketActiveBtnHandler() {
        btn_ticket.setOnClickListener {
            startActivity(Intent(this, ActiveTicketPage::class.java))
        }
    }
}