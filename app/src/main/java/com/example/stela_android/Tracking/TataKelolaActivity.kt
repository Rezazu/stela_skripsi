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

class TataKelolaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tata_kelola)

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

    fun btnTiketAktifListener() {
        btn_aktif.setOnClickListener {
            btn_aktif.background = resources.getDrawable(R.drawable.shadow_banner)
            btn_aktif.setTextColor(Color.parseColor("#FFFFFF"))

            btn_selesai.background = resources.getDrawable(R.drawable.border_blue)
            btn_selesai.setTextColor(Color.parseColor("#000000"))
        }
    }

    fun btnTiketSelesaiListener() {
        btn_selesai.setOnClickListener {
            btn_selesai.background = resources.getDrawable(R.drawable.shadow_banner)
            btn_selesai.setTextColor(Color.parseColor("#FFFFFF"))

            btn_aktif.background = resources.getDrawable(R.drawable.border_blue)
            btn_aktif.setTextColor(Color.parseColor("#000000"))
        }
    }
}