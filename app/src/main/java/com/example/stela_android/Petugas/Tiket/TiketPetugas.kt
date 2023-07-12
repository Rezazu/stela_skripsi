package com.example.stela_android.Petugas.Tiket

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.Tiket
import com.example.stela_android.Retrofit.Ticket.TiketAdapter
import com.example.stela_android.Retrofit.Ticket.TiketApi
import com.example.stela_android.Retrofit.Ticket.TiketResponse
import kotlinx.android.synthetic.main.fragment_sistem_informasi.*
import kotlinx.android.synthetic.main.fragment_tata_kelola_ti.btn_dropdown
import kotlinx.android.synthetic.main.fragment_tata_kelola_ti.container_tiket
import kotlinx.android.synthetic.main.fragment_tata_kelola_ti.tv_empty_tiket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TiketPetugas : Fragment() {
    private val list = ArrayList<Tiket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = this.arguments
        return inflater.inflate(R.layout.fragment_tiket_petugas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTickets()

    }

    private fun getTickets() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)

        retro.getTickets().enqueue(object: Callback<TiketResponse> {
            override fun onResponse(call: Call<TiketResponse>, response: Response<TiketResponse>) {
                response.body()?.data?.let { list.addAll(it) }

                if(response.body()?.success == null) {
                    container_tiket.visibility = View.GONE
                    btn_dropdown.setImageResource(R.drawable.ic_chevron_down_is)
                    tv_empty_tiket.visibility = View.VISIBLE
                    tv_empty_tiket.text = "Anda tidak memiliki layanan aktif dalam kategori Sistem Informasi"
                } else {
                    tv_empty_tiket.visibility = View.GONE
                    rvTicketInformationSystem.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
//                        adapter = TiketAdapter(context, list, "Sistem Informasi", this@TiketPetugas)

                        val ticketAdapter = adapter
                        rvTicketInformationSystem.adapter = ticketAdapter
                        ticketAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<TiketResponse>, t: Throwable) {
                Log.d("Ticket", "onFailure: " + t.message)
            }

        })
    }


}