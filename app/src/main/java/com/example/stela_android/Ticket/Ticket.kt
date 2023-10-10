package com.example.stela_android.Ticket

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.Homepage.Notification.PostAdapter
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Notification.Notification
import com.example.stela_android.Retrofit.Notification.NotificationApi
import com.example.stela_android.Retrofit.Notification.NotificationResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.*
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranAdapter
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_ticket.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Ticket : AppCompatActivity() {
    private val petugas = ArrayList<Petugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val id = intent.getIntExtra("id",0)
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

        val myToast = Toast.makeText(applicationContext, "Tiket " + kodeTiket + " ✨", Toast.LENGTH_LONG)
        myToast.show()

        Log.d("DOKUMEN NAMES", "data: " + dokumenLampiranNames)
        Log.d("DOKUMEN PATHS", "data: " + dokumenLampiranPaths)

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
//        tv_nama_pelapor.text = namaPelapor
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
        }
        else {
            // setting if ticket has been rated, so display the stars not btn rate ticket
            if(statusTiket == 6 && rating != null) {
                rating_container.visibility = View.VISIBLE
                rating_bar.rating = rating.toFloat()
            } else {
                rating_container.visibility = View.GONE
            }
        }

        Service.statusTiketDisplay(statusTiket, tv_status_tiket)
        getPetugas(id)
    }

    private fun getPetugas(id: Int){
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)
        retro.getPetugasById(id).enqueue(object: Callback<ListPetugasResponse> {
            override fun onResponse(
                call: Call<ListPetugasResponse>,
                response: Response<ListPetugasResponse>
            ) {
                response.body()?.data?.let { petugas.addAll(it) }
                fun setPetugas(petugas: Petugas){
                    tv_namapetugas.text = petugas.nama
                    tv_ratingpetugas.text = petugas.rating.toString()
                    rating_petugas.rating = petugas.rating!!
                }
                if (petugas.isNotEmpty()){
                    setPetugas(petugas.first())
                } else {
                    petugas_tiket.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ListPetugasResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "Exception: " + t.message)
            }
        })
    }

    fun whatsapp(view: View) {
        val url =
            "https://api.whatsapp.com/send/?phone=081952434368&text&type=phone_number&app_absent=0"
        val bukeBrowser = Intent(Intent.ACTION_VIEW)
        bukeBrowser.data = Uri.parse(url)
        startActivity(bukeBrowser)
    }


}





