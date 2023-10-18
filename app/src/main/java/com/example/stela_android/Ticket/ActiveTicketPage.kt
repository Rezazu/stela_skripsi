package com.example.stela_android.Ticket

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Ticket.*
import kotlinx.android.synthetic.main.activity_active_ticket_page.*
import kotlinx.android.synthetic.main.activity_home_prakom.*
import kotlinx.android.synthetic.main.activity_profile.*

class ActiveTicketPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_active_ticket_page, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnTiketAktifListener()
        btnTiketSelesaiListener()
        refreshAction()
        ll_selesai.visibility = View.GONE
    }

    fun refreshAction() {
        refreshTiket.setOnRefreshListener {
            parentFragmentManager.beginTransaction().detach(this).commit()
            parentFragmentManager.beginTransaction().attach(this).commit()
            Toast.makeText(context,"Page Refreshed!", Toast.LENGTH_SHORT).show()
            refreshTiket.isRefreshing = false
        }
    }

    fun btnTiketAktifListener() {
        btn_aktif.setOnClickListener {
            btn_aktif.background = resources.getDrawable(R.drawable.shadow_banner)
            btn_aktif.setTextColor(Color.parseColor("#FFFFFF"))

            btn_selesai.background = resources.getDrawable(R.drawable.border_blue_rounded)
            btn_selesai.setTextColor(Color.parseColor("#000000"))

            val myToast = Toast.makeText(context, "Tiket Aktif ✨", Toast.LENGTH_LONG)
            myToast.show()

            ll_selesai.visibility = View.GONE

            if(ll_aktif.visibility == View.GONE) {
                ll_aktif.visibility = View.VISIBLE
            }
        }
    }

    fun btnTiketSelesaiListener() {
        btn_selesai.setOnClickListener {
            btn_selesai.background = resources.getDrawable(R.drawable.shadow_banner)
            btn_selesai.setTextColor(Color.parseColor("#FFFFFF"))

            btn_aktif.background = resources.getDrawable(R.drawable.border_blue_rounded)
            btn_aktif.setTextColor(Color.parseColor("#000000"))

            val myToast = Toast.makeText(context, "Tiket Selesai ✨", Toast.LENGTH_LONG)
            myToast.show()

            ll_selesai.visibility = View.VISIBLE
            ll_aktif.visibility = View.GONE
//            ft?.commit();

        }
    }
}

