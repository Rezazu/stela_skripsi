package com.example.stela_android.Ticket

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Ticket.Petugas
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.*
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranAdapter
import com.example.stela_android.Service.Service
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.activity_ticket.tv_judul_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_solusi
import kotlinx.android.synthetic.main.activity_ticket.tv_unit_kerja_pelapor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Ticket : AppCompatActivity() {
    private val petugas = ArrayList<Petugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        val id = intent.getIntExtra("id",0)
        val noTiket = intent.getStringExtra("no_tiket").toString()
        val keterangan = intent.getStringExtra("keterangan").toString()
        btn_pengguna_selesai.visibility = View.GONE

        getTicketbyNoTiket(noTiket)
        btn_pengguna_selesai.setOnClickListener {
            DialogPenggunaSelesai(this, id, keterangan).show()
        }
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
                    tv_jabatanpetugas.text = petugas.unit_kerja
                    tv_ratingpetugas.text = petugas.rating.toString()
                    rating_petugas.rating = petugas.rating!!
                    Picasso.get().load(petugas.profile)
                        .placeholder(R.drawable.circle_1)
                        .transform(CropCircleTransformation())
                        .into(iv_petugas)
                }
                if (petugas.isNotEmpty()) {
                    setPetugas(petugas.first())
                    petugas_tiket.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ListPetugasResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "Exception: " + t.message)
            }
        })
    }

    fun getTicketbyNoTiket(no_tiket: String) {
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)
        retro.getTicketsByNoTiket(no_tiket).enqueue(object: Callback<TiketResponseById>{
            override fun onResponse(
                call: Call<TiketResponseById>,
                response: Response<TiketResponseById>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.data!!
                    getPetugas(result.id)
                    if(result.keterangan?.length!! >= 45) {
                        tv_judul_tiket.setText(Service.judulSubStr(result.keterangan))
                    } else {
                        tv_judul_tiket.setText(result.keterangan)
                    }
                    tv_kode_tiket.text = result.no_tiket
                    tv_tanggal_tiket.text = Service.date(result.tanggal_input)
                    Service.statusTiketDisplay(result.id_status_tiket, tv_status_tiket)
                    tv_nama_pelapor.text = result.nama_pelapor
                    tv_jabatan_pelapor.text = result.bagian_pelapor
                    tv_unit_kerja_pelapor.text = result.unit_kerja_pelapor
                    tv_gedung_pelapor.text = result.gedung_pelapor
                    tv_lantai_pelapor.text = result.lantai_pelapor
                    tv_ruangan_pelapor.text = result.ruangan_pelapor
                    tv_keterangan.text = result.keterangan
                    tv_permasalahan_akhir.text = result.permasalahan_akhir
                    tv_solusi.text = result.solusi

                    if (result.id_status_tiket == 7) {
                        btn_pengguna_selesai.visibility = View.VISIBLE
                    }

                    if(result?.dokumen_lampiran != null) {
                        rvDokumen?.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = DokumenLampiranAdapter(
                                context,
                                result.dokumen_lampiran
                            )
                            rvDokumen.adapter = adapter
                        }
                    }

                    if(result.id_status_tiket != 6 && result.rating == null) {
                        rating_container.visibility = View.GONE
                    } else {
                        if(result.id_status_tiket == 6 && result.rating != null) {
                            rating_container.visibility = View.VISIBLE
                            rating_bar.rating = result.rating.toFloat()
                        } else {
                            rating_container.visibility = View.GONE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<TiketResponseById>, t: Throwable) {
                Log.d("Tiket", "onFailure: "+t.message)
            }

        })
    }


    fun whatsapp(view: View) {
        val url =
            "https://api.whatsapp.com/send/?phone=6281275756100&text&type=phone_number&app_absent=0"
        val bukeBrowser = Intent(Intent.ACTION_VIEW)
        bukeBrowser.data = Uri.parse(url)
        startActivity(bukeBrowser)
    }
}





