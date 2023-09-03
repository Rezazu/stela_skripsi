package com.example.stela_android.Petugas.Tiket

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.stela_android.Petugas.HomepagePrakom
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.PetugasTiketApi
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.activity_ticket.tv_gedung_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_judul_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_keterangan
import kotlinx.android.synthetic.main.activity_ticket.tv_kode_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_lantai_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_permasalahan_akhir
import kotlinx.android.synthetic.main.activity_ticket.tv_ruangan_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_solusi
import kotlinx.android.synthetic.main.activity_ticket.tv_tanggal_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_unit_kerja_pelapor
import kotlinx.android.synthetic.main.activity_ticket_petugas.*
import kotlinx.android.synthetic.main.activity_ticket_petugas.back_btn
import kotlinx.android.synthetic.main.ticket_item.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class TiketPetugasItem : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    var selectedFile = ""
    val filePaths: ArrayList<String> = ArrayList()

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_petugas)
        val judul = intent.getStringExtra("judul")
        val kodeTiket = intent.getStringExtra("kode_tiket")
        val tanggalTiket = intent.getStringExtra("tanggal_permintaan")
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

        val myToast = Toast.makeText(applicationContext, "Tiket " + kodeTiket + " ✨", Toast.LENGTH_LONG)
        myToast.show()

        Log.d("DOKUMEN NAMES", "data: " + dokumenLampiranNames)
        Log.d("DOKUMEN PATHS", "data: " + dokumenLampiranPaths)

//        rvDokumen.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(context)
//            // set the custom adapter to the RecyclerView
//            adapter = DokumenLampiranAdapter(context, dokumenLampiranNames!!, dokumenLampiranPaths!!)
//            rvDokumen.adapter = adapter
//        }

        if(judul?.length!! >= 45) {
            tv_judul_tiket.setText(Service.judulSubStr(judul))
        } else {
            tv_judul_tiket.setText(judul)
        }
        val id = intent.getIntExtra("id", 193)
        tv_kode_tiket.text = id.toString()
        tv_tanggal_tiket.text = tanggalTiket
        tv_nama_pelapor_2.text = namaPelapor
        tv_bagian_pelapor_2.text = jabatanPelapor
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
//                rating_container.visibility = View.VISIBLE
                rating_bar_petugas.rating = rating.toFloat()
                button_section.visibility = View.GONE
                bottom_container.visibility = View.GONE
            } else {
//                rating_container.visibility = View.GONE
            }
        }

        val btn_kerjakan = findViewById<Button>(R.id.btn_kerjakan)
        val btn_kendala = findViewById<Button>(R.id.btn_kendala)
        btn_kerjakan.setOnClickListener {
            DialogSelesai(this, id, keterangan).show()
        }
        btn_kendala.setOnClickListener {
            DialogTerkendala(this, id, keterangan).show()
        }
        backBtnListener()
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, HomepagePrakom::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}