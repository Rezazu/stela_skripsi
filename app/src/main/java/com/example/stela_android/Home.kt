package com.example.stela_android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class Home : Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_permohonan = requireActivity().findViewById<ImageButton>(R.id.btn_permohonan)
        btn_permohonan.setOnClickListener{
            val intent_permohonan = Intent(activity, FormActivity::class.java)
            startActivity(intent_permohonan)
        }

        btnNotificationListener()
        btnInformationSystemListener()
        btnInfrastructureJaringanListener()
        btnTataKelolaTIListener()
        btnLainnyaListener()
    }

    private fun btnNotificationListener() {
        btn_notification.setOnClickListener{
            startActivity(Intent(activity, NotificationsPage::class.java))
        }
    }

    private fun btnInformationSystemListener() {
        btn_sistem_informasi.setOnClickListener{
            startActivity(Intent(activity, SistemInformasiActivity::class.java))
        }
    }

    private fun btnInfrastructureJaringanListener() {
        btn_infrastruktur_jaringan.setOnClickListener{
            startActivity(Intent(activity, InfrastrukturJaringanActivity::class.java))
        }
    }

    private fun btnTataKelolaTIListener() {
        btn_tata_kelola.setOnClickListener{
            startActivity(Intent(activity, TataKelolaActivity::class.java))
        }
    }

    private fun btnLainnyaListener() {
        btn_lainnya.setOnClickListener{
            startActivity(Intent(activity, LainnyaActivity::class.java))
        }
    }
}