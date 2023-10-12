package com.example.stela_android.Petugas.Tiket

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.Petugas.HomepagePrakom
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.PermintaanResponseById
import com.example.stela_android.Retrofit.Petugas.PetugasTiketApi
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranAdapter
import com.example.stela_android.Retrofit.Ticket.LaporanPetugas.LaporanPetugasAdapter
import com.example.stela_android.Retrofit.Ticket.TiketApi
import com.example.stela_android.Retrofit.Ticket.TiketResponseById
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.activity_ticket.tv_gedung_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_judul_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_keterangan
import kotlinx.android.synthetic.main.activity_ticket.tv_kode_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_lantai_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_permasalahan_akhir
import kotlinx.android.synthetic.main.activity_ticket.tv_ruangan_pelapor
import kotlinx.android.synthetic.main.activity_ticket.tv_solusi
import kotlinx.android.synthetic.main.activity_ticket.tv_tanggal_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_unit_kerja_pelapor
import kotlinx.android.synthetic.main.activity_ticket_petugas.*
import kotlinx.android.synthetic.main.activity_ticket_petugas.back_btn
import kotlinx.android.synthetic.main.activity_ticket_petugas.tv_nama_pelapor_2
import kotlinx.android.synthetic.main.activity_ticket_petugas_selesai.*
import kotlinx.android.synthetic.main.popup_laporan_selesai.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TiketPetugasItem : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    var selectedFile = ""
    val filePaths: ArrayList<String> = ArrayList()
    private val dokumenLampiranNames = ArrayList<String>()
    private val dokumenLampiranPaths = ArrayList<String>()
    private val dokumenLampiranExts = ArrayList<String>()
    private val laporanPetugasNames = ArrayList<String>()
    private val laporanPetugasPaths = ArrayList<String>()
    private val laporanPetugasExts = ArrayList<String>()

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_petugas)
        val judul = intent.getStringExtra("judul")
        val id = intent.getIntExtra("id", 0)
        val keterangan = intent.getStringExtra("keterangan")

        if(judul?.length!! >= 45) {
            tv_judul_tiket.setText(Service.judulSubStr(judul))
        } else {
            tv_judul_tiket.setText(judul)
        }
        val btn_kerjakan = findViewById<Button>(R.id.btn_kerjakan)
        val btn_kendala = findViewById<Button>(R.id.btn_kendala)
        var eskalasi = ""

        btn_kerjakan.setOnClickListener {
            eskalasi = "selesai"
            DialogSelesai(this, id, keterangan, eskalasi).show()
        }
        btn_kendala.setOnClickListener {
            eskalasi = "kendala"
            DialogSelesai(this, id, keterangan, eskalasi).show()
        }

        backBtnListener()
        getPermintaan(id)
    }
    private fun getPermintaan(id: Int){
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(PetugasTiketApi::class.java)
        retro.getPermintaanById(id).enqueue(object: Callback<PermintaanResponseById> {
            override fun onResponse(
                call: Call<PermintaanResponseById>,
                response: Response<PermintaanResponseById>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.data!!
                    tv_kode_tiket_petugas.text = result.no_tiket
                    tv_tanggal_tiket_petugas.text = Service.date(result.tanggal_input)
//                    Service.statusTiketDisplay(result.id_status_tiket, tv_status_tiket)
                    tv_nama_pelapor_2.text = result.nama_pelapor
                    tv_jabatan_pelapor_2.text = result.bagian_pelapor
                    tv_unit_kerja_pelapor.text = result.unit_kerja_pelapor
                    tv_email_pelapor_2.text = result.email_pelapor
                    tv_hp_pelapor_2.text = result.hp_pelapor
                    tv_keterangan_petugas.text = result.keterangan
                    tv_permasalahan_akhir_petugas.text = result.permasalahan_akhir
                    tv_solusi_petugas.text = result.solusi

                    tv_lokasi2.text = "${result.gedung_pelapor}, Lantai ${result.lantai_pelapor}, Ruang ${result.ruangan_pelapor}"

                    if(result.id_status_tiket != 6 && result.rating == 0) {
                        rating_bar_petugas.visibility = View.GONE
                        tv_rating_keterangan.visibility = View.GONE
                    } else {
                        if(result.id_status_tiket == 6 && result.rating != null) {
                            rating_bar_petugas.rating = result.rating.toFloat()
                            button_section.visibility = View.GONE
                            bottom_container.visibility = View.GONE
                            btn_wa.visibility = View.GONE
                            tv_rating_keterangan.text = result.keterangan_rating
                        }
                    }
                    if(result?.dokumen_lampiran != null) {
                        rvDokumenPetugas?.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = DokumenLampiranAdapter(
                                context,
                                result.dokumen_lampiran
                            )
                            rvDokumenPetugas.adapter = adapter
                        }
                    }

                    if(result?.laporan_petugas != null) {
                        rvLaporanPetugas?.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = LaporanPetugasAdapter(
                                context,
                                result.laporan_petugas
                            )
                            rvLaporanPetugas.adapter = adapter
                        }
                    }
                    if(result.id_status_tiket != 6 && result.rating == null) {
                        rating_bar_petugas.visibility = View.GONE
                    } else {
                        if(result.id_status_tiket == 6 && result.rating != null) {
                            rating_bar_petugas.visibility = View.VISIBLE
                            rating_bar_petugas.rating = result.rating.toFloat()
                        } else {
                            rating_bar_petugas.visibility = View.GONE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PermintaanResponseById>, t: Throwable) {
                Log.d("Tiket Petugas", "onFailure: "+t.message)
            }
        })
    }

    internal fun selectFile(){
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takeFile.launch(intent)
    }

    internal fun getSelectedFile(): String {
        return selectedFile
    }

    internal fun getFilePaths(): ArrayList<String> {
        return filePaths
    }

    private val takeFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            if(result.data?.clipData != null){
                var count = result.data?.clipData?.itemCount
                var currentItem = 0
                Log.d("data", ":"+count)
                while(currentItem < count!!){
                    var selectedFile = result.data?.clipData?.getItemAt(currentItem)?.uri
                    filePaths.add(selectedFile.toString())
                    currentItem++
                }
                tv_file.setText(count.toString()+" files selected")
            }else if(result.data != null){
                var path = result.data?.data as Uri
                var nameFile = result.data?.data?.lastPathSegment.toString()
                this.selectedFile = path.toString()
                val tvLaporan = findViewById<TextView>(R.id.tv_filelaporan)
//                tvLaporan.setText(nameFile.substring(nameFile.lastIndexOf("/")+1))
            }
        }
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, HomepagePrakom::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }

    fun whatsappPetugas(view: View) {
        val hpPelapor = intent.getIntExtra("hp",0)
        val url =
            "https://api.whatsapp.com/send/?phone=$hpPelapor&text&type=phone_number&app_absent=0"
        val bukeBrowser = Intent(Intent.ACTION_VIEW)
        bukeBrowser.data = Uri.parse(url)
        startActivity(bukeBrowser)
    }
}