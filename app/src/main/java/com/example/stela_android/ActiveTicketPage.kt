package com.example.stela_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_active_ticket_page.*;

class ActiveTicketPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_ticket_page)

        hideTicketsInformationSystem()
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }

    private fun hideTicketsInformationSystem() {
        dropdown_handler_1.setOnClickListener {
            tickets_section_1.visibility = if(true) View.VISIBLE else View.GONE
            dropdown_handler_1.setImageResource()
        }
    }

}