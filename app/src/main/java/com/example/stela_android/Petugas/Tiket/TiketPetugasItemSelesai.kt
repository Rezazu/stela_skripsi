package com.example.stela_android.Petugas.Tiket

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.Login.Login
import com.example.stela_android.Petugas.HomepagePrakom
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranAdapter
import com.example.stela_android.Service.Service
import com.example.stela_android.Storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.activity_ticket.rvDokumen
import kotlinx.android.synthetic.main.activity_ticket.tv_gedung_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_judul_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_keterangan
import kotlinx.android.synthetic.main.activity_ticket.tv_kode_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_lantai_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_nama_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_permasalahan_akhir
import kotlinx.android.synthetic.main.activity_ticket.tv_ruangan_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_solusi
import kotlinx.android.synthetic.main.activity_ticket.tv_tanggal_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_unit_kerja_pelapor
import kotlinx.android.synthetic.main.activity_ticket_petugas.*
import kotlinx.android.synthetic.main.activity_ticket_petugas.back_btn

class TiketPetugasItemSelesai : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_petugas_selesai)
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

        tv_kode_tiket.text = kodeTiket
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
//                rating_bar.rating = rating.toFloat()
                var lanjutan : Button = findViewById(R.id.btn_lanjutan)
                var kerjakan : Button = findViewById(R.id.btn_kerjakan)
                lanjutan.visibility = View.GONE
                kerjakan.visibility = View.GONE
            } else {
//                rating_container.visibility = View.GONE
            }
        }

//        Service.statusTiketDisplay(statusTiket, tv_status_tiket)
    kerjakanBtnListenebr()
        backBtnListener()
    }
    private fun showDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.popup_laporan_petugas)
//        val logout = dialog.findViewById<Button>(R.id.btn_logout)
        val dismiss = dialog.findViewById<Button>(R.id.btn_kembali)
        dialog.show()
//        logout.setOnClickListener {
//            getActivity()?.let { it1 -> SharedPrefManager.getInstance(it1.getApplicationContext()).clear() }
//            if(!SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
//                val intent = Intent(activity, Login::class.java)
//                startActivity(intent)
//            }
//        }
        dismiss.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun kerjakanBtnListenebr(){
        val btn_kerjakan = findViewById<Button>(R.id.btn_kerjakan)
        btn_kerjakan.setOnClickListener {
            showDialog()
        }
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, HomepagePrakom::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}