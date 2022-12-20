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
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.dokumen_item.*
import kotlinx.android.synthetic.main.fragment_infrastruktur_jaringan.*
import kotlinx.android.synthetic.main.ticket_item.view.*


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
        val statusTiket = intent.getIntExtra("statusTiket", 0)
        val rating = intent.getIntExtra("rating", 0)

        val dokumenLampiranNames = intent.getStringArrayListExtra("dokumenLampiranNames")
        val dokumenLampiranPaths = intent.getStringArrayListExtra("dokumenLampiranPaths")

        Log.d("DOKUMEN NAMES", "data: " + dokumenLampiranNames)
        Log.d("DOKUMEN PATHS", "data: " + dokumenLampiranPaths)
//        val dokumenLampiran = intent.getParcelableArrayListExtra("dokumen_lampiran", DokumenLampiranResponse::class.java)
//        Log.d("DOKUMEN LAMPIRAN", "msg: " + dokumenLampiran)

//        val filelist = intent.getParcelableArrayExtra("DOKUMEN_LAMPIRAN")
//        Log.d("DOKUMEN LAMPIRAN", "data: " + filelist)

        rvDokumen.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(context)
            // set the custom adapter to the RecyclerView
            adapter = DokumenLampiranAdapter(context, dokumenLampiranNames!!, dokumenLampiranPaths!!)
            rvDokumen.adapter = adapter
        }

        if(judul?.length!! >= 45) {
            tv_judul_tiket.setText(Service.judulSubStr(judul))
        } else {
            tv_judul_tiket.setText(judul)
        }

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

        if(statusTiket != 6 && rating == null) {
            rating_container.visibility = View.GONE
        } else {
            // setting if ticket has been rated, so display the stars not btn rate ticket
            if(statusTiket == 6 && rating != null) {
                rating_container.visibility = View.VISIBLE
                rating_bar.rating = rating.toFloat()
            } else {
                rating_container.visibility = View.GONE
            }
        }

        Service.statusTiketDisplay(statusTiket, tv_status_tiket)

    }

    fun whatsapp(view: View) {
        val url =
            "https://api.whatsapp.com/send/?phone=081952434368&text&type=phone_number&app_absent=0"
        val bukeBrowser = Intent(Intent.ACTION_VIEW)
        bukeBrowser.data = Uri.parse(url)
        startActivity(bukeBrowser)
    }

}





