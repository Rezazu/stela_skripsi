package com.example.stela_android.Petugas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Homepage.Notification.NotificationsPage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Petugas.PermintaanResponse
import com.example.stela_android.Retrofit.Petugas.PetugasTiketApi
import com.example.stela_android.Retrofit.Petugas.TiketPetugasAdapter
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.*
import com.example.stela_android.Retrofit.Ticket.TiketPetugas.TiketPetugas
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_home.btn_notification
import kotlinx.android.synthetic.main.activity_home_prakom.*
import kotlinx.android.synthetic.main.fragment_tata_kelola_ti.tv_empty_tiket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePrakom : Fragment(), OnTicketClickListener {

    private val list = ArrayList<TiketPetugas>()
    private val layoutManager: RecyclerView.LayoutManager? = null
    private val adapter: RecyclerView.Adapter<TiketPetugasAdapter.TicketViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home_prakom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        getTickets()
        btnNotificationListener()
    }

    private fun getData(){
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(UserApi::class.java)
        val tv_name : TextView = requireActivity().findViewById(R.id.tv_name) as TextView
        val tv_welcomeNama : TextView = requireActivity().findViewById(R.id.welcomenama) as TextView
        val tv_dept : TextView = requireActivity().findViewById(R.id.tv_dept) as TextView
        retro.getUser().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseData = response.body()?.data
                val header = responseData?.user
                if(SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                    val nama = prefs?.getString("nama_lengkap", "")
                    tv_name.text = nama
                    tv_welcomeNama.text = nama
                    val dept = prefs?.getString("bagian", "")
                    tv_dept.text = dept.toString()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+ t.message)
            }

        })
    }

    private fun getTickets() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(PetugasTiketApi::class.java)
        val tv_permintaan : TextView = requireActivity().findViewById(R.id.tv_permintaan) as TextView


        retro.getPermintaan().enqueue(object: Callback<PermintaanResponse> {
            override fun onResponse(call: Call<PermintaanResponse>, response: Response<PermintaanResponse>) {
                response.body()?.data?.let { list.addAll(it) }
                val jumlah_tiket : Int = list.count()
                tv_permintaan.setText("Anda memiliki " + jumlah_tiket + " permintaan baru yang belum di kerjakan")


                if(response.body()?.success == null) {
                    container_tiket_prakom.visibility = View.GONE
//                    btn_dropdown.setImageResource(R.drawable.ic_chevron_down_is)
                    tv_empty_tiket.visibility = View.VISIBLE
                    tv_empty_tiket.text = "Anda tidak memiliki permintaan aktif"
                } else {
//                    tv_empty_tiket.visibility = View.GONE
                    rvTicketPetugas.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
                        adapter = TiketPetugasAdapter(context, list, this@HomePrakom)

                        val ticketAdapter = adapter
                        rvTicketPetugas.adapter = ticketAdapter
                        ticketAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<PermintaanResponse>, t: Throwable) {
                Log.d("Ticket", "onFailure: " + t.message)
            }

        })
    }

    private fun btnNotificationListener() {
        btn_notification.setOnClickListener{
            startActivity(Intent(activity, NotificationsPage::class.java))
        }
    }

    override fun onTicketItemClicked(position: Int) {
        TODO("Not yet implemented")
    }

//    override fun onTicketItemClicked(position: Int) {
//        val intent = Intent(activity, Ticket::class.java)
//
//        intent.putExtra("judul", list[position]?.keterangan)
//        intent.putExtra("kode_tiket", list[position]?.no_tiket)
//        intent.putExtra("tanggal_permintaan", list[position]?.tanggal_input)
//        intent.putExtra("nama", list[position]?.nama_pelapor)
//        intent.putExtra("jabatan", list[position]?.bagian_pelapor)
//        intent.putExtra("unit_kerja", list[position]?.unit_kerja_pelapor)
//        intent.putExtra("gedung", list[position]?.gedung_pelapor)
//        intent.putExtra("lantai", list[position]?.lantai_pelapor)
//        intent.putExtra("ruangan", list[position]?.ruangan_pelapor)
//        intent.putExtra("status", list[position]?.status)
//
//        intent.putExtra("keterangan", list[position]?.keterangan)
//        intent.putExtra("permasalahan_akhir", list[position]?.permasalahan_akhir)
//        intent.putExtra("solusi", list[position]?.solusi)
//        intent.putExtra("statusTiket", list[position]?.id_status_tiket)
//        intent.putExtra("rating", list[position]?.rating)
//
//        if(list[position]?.dokumen_lampiran != null) {
//            val sizeOfDokumenLampiran: Int? = list[position]?.dokumen_lampiran?.size
//            val dokumenLampiranNames: ArrayList<String> = ArrayList<String>()
//            val dokumenLampiranPaths: ArrayList<String> = ArrayList<String>()
//            for(nums in 0 until sizeOfDokumenLampiran!!) {
//                list[position]?.dokumen_lampiran?.get(nums)?.original_name?.let {
//                    dokumenLampiranNames.add(nums,
//                        it
//                    )
//                }
//
//                list[position]?.dokumen_lampiran?.get(nums)?.path?.let {
//                    dokumenLampiranPaths.add(nums,
//                        it
//                    )
//                }
//            }
//
//            intent.putExtra("dokumenLampiranNames", dokumenLampiranNames)
//            intent.putExtra("dokumenLampiranPaths", dokumenLampiranPaths)
//        } else {
//            intent.putExtra("dokumenLampiranNames", ArrayList<String>())
//            intent.putExtra("dokumenLampiranPaths", ArrayList<String>())
//        }
//
//        startActivity(intent)
//    }

}

