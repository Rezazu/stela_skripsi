package com.example.stela_android.Petugas.Tiket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.*
import com.example.stela_android.Retrofit.Retrofit
import kotlinx.android.synthetic.main.fragment_tiket_petugas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TiketPetugasFragment : Fragment(), OnTicketPetugasClickListener {

    private val list = ArrayList<TiketPetugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tiket_petugas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                if (response.body()?.success == null) {
                    tv_empty_tiket.visibility = View.VISIBLE
                    tv_empty_tiket.text = "Anda tidak memiliki permintaan aktif"
                } else {
                    tv_empty_tiket.visibility = View.GONE
                    rvTicketPetugas.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = context?.let { TiketPetugasAdapter(it, list, this@TiketPetugasFragment) }
                        val ticketAdapter = adapter
                        rvTicketPetugas.adapter = ticketAdapter
                    }
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
        intent.putExtra("judul", list[position]?.keterangan)
        intent.putExtra("kode_tiket", list[position]?.no_tiket)
        intent.putExtra("tanggal_permintaan", list[position]?.tanggal)
        intent.putExtra("nama", list[position]?.nama_pelapor)
        intent.putExtra("jabatan", list[position]?.bagian_pelapor)
        intent.putExtra("unit_kerja", list[position]?.unit_kerja_pelapor)
        intent.putExtra("hp", list[position]?.hp_pelapor)
        intent.putExtra("gedung", list[position]?.gedung_pelapor)
        intent.putExtra("lantai", list[position]?.lantai_pelapor)
        intent.putExtra("ruangan", list[position]?.ruangan_pelapor)
        intent.putExtra("status", list[position]?.id_status_tiket)
        intent.putExtra("keterangan", list[position]?.keterangan)
        intent.putExtra("permasalahan_akhir", list[position]?.permasalahan_akhir)
        intent.putExtra("solusi", list[position]?.solusi)
        intent.putExtra("statusTiket", list[position]?.id_status_tiket)
        intent.putExtra("rating", list[position]?.rating)
        intent.putExtra("keterangan_rating",list[position].keterangan_rating)

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

        if(list[position]?.laporan_petugas != null){
            val sizeOfLaporanPetugas: Int? = list[position]?.laporan_petugas?.size
            val laporanPetugasNames: ArrayList<String> = ArrayList<String>()
            val laporanPetugasPaths: ArrayList<String> = ArrayList<String>()
            for(nums in 0 until sizeOfLaporanPetugas!!) {
                list[position]?.laporan_petugas?.get(nums)?.original_name?.let {
                    laporanPetugasNames.add(nums,
                        it
                    )
                }

                list[position]?.laporan_petugas?.get(nums)?.path?.let {
                    laporanPetugasPaths.add(nums,
                        it
                    )
                }
            }
            intent.putExtra("laporanPetugasNames", laporanPetugasNames)
            intent.putExtra("laporanPetugasPaths", laporanPetugasPaths)
        } else {
            intent.putExtra("laporanPetugasNames", ArrayList<String>())
            intent.putExtra("laporanPetugasPaths", ArrayList<String>())
        }
        startActivity(intent)
    }
}