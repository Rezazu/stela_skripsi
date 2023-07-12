package com.example.stela_android.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.*
import com.example.stela_android.Ticket.Ticket
import kotlinx.android.synthetic.main.fragment_infrastruktur_jaringan.*
import kotlinx.android.synthetic.main.fragment_infrastruktur_jaringan.btn_dropdown
import kotlinx.android.synthetic.main.fragment_infrastruktur_jaringan.container_tiket
import kotlinx.android.synthetic.main.fragment_infrastruktur_jaringan.tv_empty_tiket
import retrofit2.*


class InfrastrukturJaringanFragment: Fragment(), OnTicketClickListener {

    private val list = ArrayList<Tiket>()
    private val layoutManager: RecyclerView.LayoutManager? = null
    private val adapter: RecyclerView.Adapter<TiketAdapter.TicketViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_infrastruktur_jaringan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTickets()
        hideShowTicketsInfrastrukturJaringan()
    }

    private fun hideShowTicketsInfrastrukturJaringan() {
        btn_dropdown.setOnClickListener {
            if(container_tiket.isVisible) {
                container_tiket.visibility = View.GONE
                btn_dropdown.setImageResource(R.drawable.ic_chevron_down_ij)
            } else  {
                container_tiket.visibility = View.VISIBLE
                btn_dropdown.setImageResource(R.drawable.ic_chevron_up_ij)
            }
        }
    }

    private fun getTickets() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)

        retro.getTicketsByCategory(2).enqueue(object: Callback<TiketResponse> {
            override fun onResponse(call: Call<TiketResponse>, response: Response<TiketResponse>) {
                response.body()?.data?.let { list.addAll(it) }

                if(response.body()?.success == null) {
                    container_tiket.visibility = View.GONE
                    btn_dropdown.setImageResource(R.drawable.ic_chevron_down_ij)
                    tv_empty_tiket.visibility = View.VISIBLE
                    tv_empty_tiket.text = "Anda tidak memiliki layanan aktif dalam kategori Infrastruktur Jaringan"
                } else {
                    tv_empty_tiket.visibility = View.GONE

                    rvTicketInfrastukturJaringan.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
                        adapter = TiketAdapter(context, list, "Infrastruktur Jaringan", this@InfrastrukturJaringanFragment)
                    }
                }
            }

            override fun onFailure(call: Call<TiketResponse>, t: Throwable) {
                Log.d("Ticket", "onFailure: " + t.message)
            }

        })


    }

    override fun onTicketItemClicked(position: Int) {
        val intent = Intent(activity, Ticket::class.java)

        intent.putExtra("judul", list[position]?.keterangan)
        intent.putExtra("kode_tiket", list[position]?.no_tiket)
        intent.putExtra("tanggal_permintaan", list[position]?.tanggal_input)
        intent.putExtra("nama", list[position]?.nama_pelapor)
        intent.putExtra("jabatan", list[position]?.bagian_pelapor)
        intent.putExtra("unit_kerja", list[position]?.unit_kerja_pelapor)
        intent.putExtra("gedung", list[position]?.gedung_pelapor)
        intent.putExtra("lantai", list[position]?.lantai_pelapor)
        intent.putExtra("ruangan", list[position]?.ruangan_pelapor)
        intent.putExtra("status", list[position]?.status)

        intent.putExtra("keterangan", list[position]?.keterangan)
        intent.putExtra("permasalahan_akhir", list[position]?.permasalahan_akhir)
        intent.putExtra("solusi", list[position]?.solusi)
        intent.putExtra("statusTiket", list[position]?.id_status_tiket)
        intent.putExtra("rating", list[position]?.rating)

        if(list[position]?.dokumen_lampiran != null) {
            val sizeOfDokumenLampiran: Int? = list[position]?.dokumen_lampiran?.size

            val dokumenLampiranNames: ArrayList<String> = ArrayList<String>()
            val dokumenLampiranPaths: ArrayList<String> = ArrayList<String>()

            for(nums in 0 until sizeOfDokumenLampiran!!) {
                list[position]?.dokumen_lampiran?.get(nums)?.original_name?.let {
                    dokumenLampiranNames.add(nums,
                        it
                    )
                }

                list[position]?.dokumen_lampiran?.get(nums)?.path?.let {
                    dokumenLampiranPaths.add(nums,
                        it
                    )
                }
            }

            intent.putExtra("dokumenLampiranNames", dokumenLampiranNames)
            intent.putExtra("dokumenLampiranPaths", dokumenLampiranPaths)
        } else {
            intent.putExtra("dokumenLampiranNames", ArrayList<String>())
            intent.putExtra("dokumenLampiranPaths", ArrayList<String>())
        }

        startActivity(intent)
    }

}