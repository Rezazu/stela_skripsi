package com.example.stela_android.Petugas.Tiket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.*
import com.example.stela_android.Retrofit.Retrofit
import kotlinx.android.synthetic.main.fragment_tiket_petugas.*
import kotlinx.android.synthetic.main.fragment_tiket_petugas_selesai.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TiketPetugasSelesaiFragment : Fragment(), OnTicketPetugasClickListener {

    private val list = ArrayList<TiketPetugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tiket_petugas_selesai, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.clear()
        getTicketPetugas()
    }

    private fun getTicketPetugas() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(PetugasTiketApi::class.java)

        retro.getPermintaan().enqueue(object : Callback<PermintaanResponse> {
            override fun onResponse(
                call: Call<PermintaanResponse>,
                response: Response<PermintaanResponse>
            ) {
                response.body()?.data?.let { list.addAll(it) }
                val tvEmptyTiket = view?.findViewById<TextView>(R.id.tv_empty_tiket_selesai_petugas)
                if (list.isNotEmpty()) {
                    tvEmptyTiket?.visibility = View.GONE
                    val rvTicketPetugas = view?.findViewById<RecyclerView>(R.id.rvTicketPetugasSelesai)
                    rvTicketPetugas?.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = context?.let { TiketPetugasSelesaiAdapter(it,list, this@TiketPetugasSelesaiFragment) }
                        val ticketAdapter = adapter
                        rvTicketPetugasSelesai.adapter = ticketAdapter
                    }
                } else {
                    tvEmptyTiket?.visibility = View.VISIBLE
                }
            }
            override fun onFailure(call: Call<PermintaanResponse>, t: Throwable) {
                Log.d("Ticket", "onFailure: " + t.message)
            }
        })
    }

    override fun onTicketPetugasItemClicked(position: Int) {
        val intent = Intent(activity, TiketPetugasItem::class.java)
        intent.putExtra("id", list[position]?.id_tiket)
        intent.putExtra("no_tiket", list[position]?.no_tiket)
        intent.putExtra("judul", list[position]?.keterangan)
        startActivity(intent)
    }
}