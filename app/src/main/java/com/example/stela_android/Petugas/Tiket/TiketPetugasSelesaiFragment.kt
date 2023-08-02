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
import kotlinx.android.synthetic.main.fragment_tata_kelola_ti.tv_empty_tiket
import kotlinx.android.synthetic.main.fragment_tiket_petugas_selesai.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TiketPetugasSelesaiFragment : Fragment(), OnTicketPetugasClickListener {

    private val list = ArrayList<TiketPetugas>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<TiketPetugasSelesaiAdapter.TicketViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket_petugas_selesai, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTicketPetugas()
    }

    private fun getTicketPetugas() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(PetugasTiketApi::class.java)
//        val tv_permintaan: TextView = requireActivity().findViewById(R.id.tv_permintaan) as TextView

        retro.getPermintaan().enqueue(object : Callback<PermintaanResponse> {
            override fun onResponse(
                call: Call<PermintaanResponse>,
                response: Response<PermintaanResponse>
            ) {
                response.body()?.data?.let { list.addAll(it) }
               
                if (response.body()?.success == null) {
//                    container_tiket_prakom.visibility = View.GONE
                    tv_empty_tiket.visibility = View.VISIBLE
                    tv_empty_tiket.text = "Anda tidak memiliki permintaan selesai"
                } else {
                    tv_empty_tiket.visibility = View.GONE
                    rvTicketPetugasSelesai.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
                        adapter = TiketPetugasSelesaiAdapter(context, list, this@TiketPetugasSelesaiFragment)
//                        ticketAdapter?.notifyDataSetChanged()
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
//
        intent.putExtra("judul", list[position]?.keterangan)
        intent.putExtra("kode_tiket", list[position]?.no_tiket)
        intent.putExtra("tanggal_permintaan", list[position]?.tanggal)
        intent.putExtra("nama", list[position]?.nama_pelapor)
        intent.putExtra("jabatan", list[position]?.bagian_pelapor)
        intent.putExtra("unit_kerja", list[position]?.unit_kerja_pelapor)
        intent.putExtra("gedung", list[position]?.gedung_pelapor)
        intent.putExtra("lantai", list[position]?.lantai_pelapor)
        intent.putExtra("ruangan", list[position]?.ruangan_pelapor)
        intent.putExtra("status", list[position]?.id_status_tiket)

        intent.putExtra("keterangan", list[position]?.keterangan)
        intent.putExtra("permasalahan_akhir", list[position]?.permasalahan_akhir)
        intent.putExtra("solusi", list[position]?.solusi)
        intent.putExtra("statusTiket", list[position]?.id_status_tiket)
        intent.putExtra("rating", list[position]?.rating)
//
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