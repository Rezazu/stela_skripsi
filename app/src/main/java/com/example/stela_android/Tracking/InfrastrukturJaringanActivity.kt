package com.example.stela_android.Tracking

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stela_android.Form.FormActivity
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_active_ticket_page.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_notifications_page.*

class InfrastrukturJaringanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infrastruktur_jaringan)

        backBtnListener()
        buatPermohonanBaruBtnListener()

//        btnTiketAktifListener()
//        btnTiketSelesaiListener()
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }

    private fun buatPermohonanBaruBtnListener() {
        btn_permohonan.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
    }


}