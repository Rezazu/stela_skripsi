package com.example.stela_android.Petugas.Tiket

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.stela_android.Retrofit.Ticket.DokumenLampiran.DokumenLampiranAdapter
import com.example.stela_android.Retrofit.Ticket.LaporanPetugas.LaporanPetugasAdapter
import com.example.stela_android.Service.Service
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_notifications_page.*
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.activity_ticket.tv_judul_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_keterangan
import kotlinx.android.synthetic.main.activity_ticket.tv_kode_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_permasalahan_akhir
import kotlinx.android.synthetic.main.activity_ticket.tv_solusi
import kotlinx.android.synthetic.main.activity_ticket.tv_tanggal_tiket
import kotlinx.android.synthetic.main.activity_ticket.tv_unit_kerja_pelapor
import kotlinx.android.synthetic.main.activity_ticket_petugas.*
import kotlinx.android.synthetic.main.activity_ticket_petugas.back_btn
import kotlinx.android.synthetic.main.popup_laporan_selesai.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import java.util.*


class TiketPetugasItem : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    var selectedFile = ""
    val filePaths: ArrayList<String> = ArrayList()

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_petugas)
        val judul = intent.getStringExtra("judul")
        val kodeTiket = intent.getStringExtra("kode_tiket")
        val tanggalTiket = intent.getStringExtra("tanggal_permintaan")
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
        val keteranganRating = intent.getStringExtra("keterangan_rating")
        val hpPelapor = intent.getStringExtra("hp")

        val dokumenLampiranNames = intent.getStringArrayListExtra("dokumenLampiranNames")
        val dokumenLampiranPaths = intent.getStringArrayListExtra("dokumenLampiranPaths")
        val dokumenLampiranExt = intent.getStringArrayListExtra("dokumenLampiranExt")

        val laporanPetugasNames = intent.getStringArrayListExtra("laporanPetugasNames")
        val laporanPetugasPaths = intent.getStringArrayListExtra("laporanPetugasPaths")
        val laporanPetugasExt = intent.getStringArrayListExtra("laporanPetugasExt")

        val myToast = Toast.makeText(applicationContext, "Tiket " + kodeTiket + " ✨", Toast.LENGTH_LONG)
        myToast.show()

        Log.d("DOKUMEN NAMES", "data: " + dokumenLampiranNames)
        Log.d("DOKUMEN PATHS", "data: " + dokumenLampiranPaths)

        Log.d("LAPORAN NAMES", "data: " + laporanPetugasNames)
        Log.d("LAPORAN PATHS", "data: " + laporanPetugasPaths)


        rvDokumenPetugas.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(context)
            // set the custom adapter to the RecyclerView
            adapter = DokumenLampiranAdapter(context, dokumenLampiranNames!!, dokumenLampiranPaths!!, dokumenLampiranExt!!)
            rvDokumenPetugas.adapter = adapter
        }

        if (laporanPetugasNames != null) {
            rvLaporanPetugas.apply {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    LaporanPetugasAdapter(context, laporanPetugasNames!!, laporanPetugasPaths!!,laporanPetugasExt!!)
                rvLaporanPetugas.adapter = adapter
            }
        }

        if(judul?.length!! >= 45) {
            tv_judul_tiket.setText(Service.judulSubStr(judul))
        } else {
            tv_judul_tiket.setText(judul)
        }

        val id = intent.getIntExtra("id", 0)
        tv_kode_tiket.text = kodeTiket.toString()
        tv_tanggal_tiket.text = tanggalTiket
        tv_nama_pelapor_2.text = namaPelapor
        tv_bagian_pelapor_2.text = jabatanPelapor
        tv_unit_kerja_pelapor.text = unitKerjaPelapor
        tv_hp_pelapor.text = hpPelapor.toString()
        tv_lokasi2.text = "$gedungPelapor, Lantai $lantaiPelapor, Ruang $ruanganPelapor"
        tv_keterangan.text = keterangan
        tv_permasalahan_akhir.text = permasalahanAkhir
        tv_solusi.text = solusi

        if(statusTiket != 6 && rating == 0) {
            rating_bar_petugas.visibility = View.GONE
        } else {
            // setting if ticket has been rated, so display the stars not btn rate ticket
            if(statusTiket == 6 && rating != null) {
                rating_bar_petugas.rating = rating.toFloat()
                button_section.visibility = View.GONE
                bottom_container.visibility = View.GONE
                btn_wa.visibility = View.GONE
                tv_rating_keterangan.text = keteranganRating

            }
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