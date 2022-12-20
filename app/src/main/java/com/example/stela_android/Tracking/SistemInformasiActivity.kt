package com.example.stela_android.Tracking

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Form.FormActivity
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.Tiket
import com.example.stela_android.Retrofit.Ticket.TiketAdapter
import com.example.stela_android.Retrofit.Ticket.TiketApi
import com.example.stela_android.Retrofit.Ticket.TiketResponse
import kotlinx.android.synthetic.main.activity_active_ticket_page.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_permohonan
import kotlinx.android.synthetic.main.activity_notifications_page.back_btn
import kotlinx.android.synthetic.main.activity_sistem_informasi.*
import kotlinx.android.synthetic.main.fragment_sistem_informasi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SistemInformasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sistem_informasi)

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