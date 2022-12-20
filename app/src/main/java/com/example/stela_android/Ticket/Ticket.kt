package com.example.stela_android.Ticket

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranAdapter
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranResponse
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.fragment_infrastruktur_jaringan.*

class Ticket : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val judul = intent.getStringExtra("judul")
        val kodeTiket = intent.getStringExtra("kode_tiket")
        val tanggalTiket = Service.date(intent.getStringExtra("tanggal_permintaan"))
        val namaPelapor = intent.getStringExtra("nama")
        val jabatanPelapor = intent.getStringExtra("jabatan")
        val unitKerjaPelapor = intent.getStringExtra("unit_kerja")
        val gedungPelapor = intent.getStringExtra("gedung")
        val lantaiPelapor = intent.getStringExtra("lantai")
        val ruanganPelapor = intent.getStringExtra("ruangan")
        val keterangan = intent.getStringExtra("keterangan")
        val permasalahanAkhir = intent.getStringExtra("permasalahan_akhir")
        val solusi = intent.getStringExtra("solusi")

//        val dokumenLampiran = intent.getParcelableArrayListExtra("dokumen_lampiran", DokumenLampiranResponse::class.java)
//        Log.d("DOKUMEN LAMPIRAN", "msg: " + dokumenLampiran)

//        val filelist = intent.getParcelableArrayExtra("DOKUMEN_LAMPIRAN")
//        Log.d("DOKUMEN LAMPIRAN", "data: " + filelist)
//        rvTicketInfrastukturJaringan.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(context)
//            // set the custom adapter to the RecyclerView
//            adapter = filelist?.let { DokumenLampiranAdapter(it) }
//        }

        tv_judul_tiket.setText(judul)
        tv_kode_tiket.text = kodeTiket
        tv_tanggal_tiket.text = tanggalTiket
        tv_nama_pelapor.text = namaPelapor
        tv_jabatan_pelapor.text = jabatanPelapor
        tv_unit_kerja_pelapor.text = unitKerjaPelapor
        tv_gedung_pelapor.text = gedungPelapor
        tv_lantai_pelapor.text = lantaiPelapor
        tv_ruangan_pelapor.text = ruanganPelapor
        tv_keterangan.text = keterangan
        tv_permasalahan_akhir.text = permasalahanAkhir
        tv_solusi.text = solusi

        back_btn2ClickHandler()
    }

    private fun back_btn2ClickHandler() {
        back_btn2.setOnClickListener {
            val i = Intent(this, ActiveTicketPage::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
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





