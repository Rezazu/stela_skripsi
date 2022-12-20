package com.example.stela_android.Ticket

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.Fragments.InfrastrukturJaringanFragment
import com.example.stela_android.Fragments.SistemInformasiFragment
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.*
import kotlinx.android.synthetic.main.activity_active_ticket_page.*;
import kotlinx.android.synthetic.main.fragment_sistem_informasi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActiveTicketPage : Fragment() {

    private val list = ArrayList<Tiket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_active_ticket_page, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        btnTiketAktifListener()
//        btnTiketSelesaiListener()
    }

//    fun btnTiketAktifListener() {
//        btn_aktif.setOnClickListener {
//            btn_aktif.background = resources.getDrawable(R.drawable.shadow_banner)
//            btn_aktif.setTextColor(Color.parseColor("#FFFFFF"))
//
//            btn_selesai.background = resources.getDrawable(R.drawable.border_blue)
//            btn_selesai.setTextColor(Color.parseColor("#000000"))
//        }
//    }

//    fun btnTiketSelesaiListener() {
//        btn_selesai.setOnClickListener {
//            btn_selesai.background = resources.getDrawable(R.drawable.shadow_banner)
//            btn_selesai.setTextColor(Color.parseColor("#FFFFFF"))
//
//            btn_aktif.background = resources.getDrawable(R.drawable.border_blue)
//            btn_aktif.setTextColor(Color.parseColor("#000000"))
//        }
//    }

}

