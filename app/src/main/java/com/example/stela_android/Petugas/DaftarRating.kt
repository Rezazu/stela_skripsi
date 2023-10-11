package com.example.stela_android.Petugas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.PermintaanResponse
import com.example.stela_android.Retrofit.Petugas.PetugasTiketApi
import com.example.stela_android.Retrofit.Petugas.ProfilePetugas.DaftarRatingAdapter
import com.example.stela_android.Retrofit.Petugas.TiketPetugas
import com.example.stela_android.Retrofit.Petugas.TiketPetugasSelesaiAdapter
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.Tiket
import com.example.stela_android.Retrofit.Ticket.TiketApi
import com.example.stela_android.Retrofit.Ticket.TiketResponse
import com.example.stela_android.Retrofit.Ticket.TiketSelesaiAdapter
import kotlinx.android.synthetic.main.activity_daftar_rating.*
import kotlinx.android.synthetic.main.fragment_tiket_petugas_selesai.*
import kotlinx.android.synthetic.main.fragment_tiket_selesai.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarRating : AppCompatActivity() {
    private val list = ArrayList<TiketPetugas>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_rating)
        getRatingPetugas()
        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun getRatingPetugas() {
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(PetugasTiketApi::class.java)

        retro.getPermintaan().enqueue(object : Callback<PermintaanResponse> {
            override fun onResponse(
                call: Call<PermintaanResponse>,
                response: Response<PermintaanResponse>
            ) {
                response.body()?.data?.let { list.addAll(it) }
                    rv_daftar_rating.recycledViewPool.setMaxRecycledViews(0, 0)
                    rv_daftar_rating.apply {
                        layoutManager = LinearLayoutManager(this@DaftarRating)
                        adapter = DaftarRatingAdapter(context, list, this@DaftarRating)
                }
            }
            override fun onFailure(call: Call<PermintaanResponse>, t: Throwable) {
                Log.d("Ticket", "onFailure: " + t.message)
            }
        })
    }
}