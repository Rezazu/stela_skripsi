package com.example.stela_android.Ticket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_ticket.*

class Ticket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        back_btn2ClickHandler()
    }

    private fun back_btn2ClickHandler() {
        back_btn2.setOnClickListener {
            startActivity(Intent(this, ActiveTicketPage::class.java))
        }
    }

    fun whatsapp(view: View) {
        val url =
            "https://api.whatsapp.com/send/?phone=081952434368&text&type=phone_number&app_absent=0"
        val bukeBrowser = Intent(Intent.ACTION_VIEW)
        bukeBrowser.data = Uri.parse(url)
        startActivity(bukeBrowser)
    }
}





