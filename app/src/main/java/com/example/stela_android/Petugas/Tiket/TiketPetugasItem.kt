package com.example.stela_android.Petugas.Tiket

import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.stela_android.Petugas.HomepagePrakom
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.PetugasTiketApi
import com.example.stela_android.Retrofit.Retrofit
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
import kotlinx.android.synthetic.main.popup_laporan_petugas.*
import kotlinx.android.synthetic.main.ticket_item.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class TiketPetugasItem : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    var selectedFile = ""
    val filePaths: ArrayList<String> = ArrayList()

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

        val dokumenLampiranNames = intent.getStringArrayListExtra("dokumenLampiranNames")
        val dokumenLampiranPaths = intent.getStringArrayListExtra("dokumenLampiranPaths")

        val myToast = Toast.makeText(applicationContext, "Tiket " + kodeTiket + " ✨", Toast.LENGTH_LONG)
        myToast.show()

        Log.d("DOKUMEN NAMES", "data: " + dokumenLampiranNames)
        Log.d("DOKUMEN PATHS", "data: " + dokumenLampiranPaths)

//        rvDokumen.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(context)
//            // set the custom adapter to the RecyclerView
//            adapter = DokumenLampiranAdapter(context, dokumenLampiranNames!!, dokumenLampiranPaths!!)
//            rvDokumen.adapter = adapter
//        }

        if(judul?.length!! >= 45) {
            tv_judul_tiket.setText(Service.judulSubStr(judul))
        } else {
            tv_judul_tiket.setText(judul)
        }
        val id = intent.getIntExtra("id", 193)
        tv_kode_tiket.text = id.toString()
        tv_tanggal_tiket.text = tanggalTiket
        tv_nama_pelapor_2.text = namaPelapor
        tv_bagian_pelapor_2.text = jabatanPelapor
        tv_unit_kerja_pelapor.text = unitKerjaPelapor
        tv_gedung_pelapor.text = gedungPelapor
        tv_lantai_pelapor.text = lantaiPelapor
        tv_ruangan_pelapor.text = ruanganPelapor
        tv_keterangan.text = keterangan
        tv_permasalahan_akhir.text = permasalahanAkhir
        tv_solusi.text = solusi

        if(statusTiket != 6 && rating == null) {
            rating_container.visibility = View.GONE
        } else {
            // setting if ticket has been rated, so display the stars not btn rate ticket
            if(statusTiket == 6 && rating != null) {
//                rating_container.visibility = View.VISIBLE
//                rating_bar.rating = rating.toFloat()
            } else {
//                rating_container.visibility = View.GONE
            }
        }

        val btn_kerjakan = findViewById<Button>(R.id.btn_kerjakan)
        btn_kerjakan.setOnClickListener {
            DialogKerjakan(this, id, keterangan).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.popup_laporan_petugas)
        val keterangan = intent.getStringExtra("keterangan")
        val tv_keterangan = dialog.findViewById<TextView>(R.id.tv_permaslahan_awal)
        tv_keterangan.text = keterangan
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateSelesai(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_laporan_petugas)
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val permasalahan_akhir = dialog.ed_permasalahan_akhir.getText().toString()
        val solusi = dialog.ed_solusi.getText().toString()
        val id_status_tiket = "6"
        val id_status_tiket_internal = "9"

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
            .addFormDataPart("permasalahan_akhir", permasalahan_akhir)
            .addFormDataPart("solusi", solusi)
            .addFormDataPart("id_status_tiket", id_status_tiket)
            .addFormDataPart("id_status_tiket_internal", id_status_tiket_internal)

        if(selectedFile != "" || filePaths.isNotEmpty()){
            if(filePaths.isEmpty()){
                val dokumen = convertFile(this.selectedFile.toUri())
                builder.addFormDataPart("dokumen[]", dokumen.name, dokumen.asRequestBody())
            }else{
                for(item in filePaths){
                    val dokumen = convertFile(item.toUri())
                    builder.addFormDataPart("dokumen[]", dokumen.name, dokumen.asRequestBody())
                }
            }
        }
        val requestBody = builder.build()
        val retro = Retrofit.postRetro(token, requestBody).create(PetugasTiketApi::class.java)
//        retro.updateSolusi(id, requestBody).enqueue(object : retrofit2.Callback<PutSolusiResponse> {
//            override fun onResponse(call: Call<PutSolusiResponse>, response: Response<PutSolusiResponse>){
//                if(response.isSuccessful){
//                    val body = response.body()
//                    val id_tiket = body?.data?.id_tiket
//                    intent.flags =
//                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
//                    startActivity(intent)
//                    this@TiketPetugasItem.finish()
//                } else {
//                    btn_submit.isEnabled = true
//                    val myToast = Toast.makeText(applicationContext, "Pastikan form tidak ada yang kosong", Toast.LENGTH_LONG)
//                    myToast.show()
//                }
//            }
//
//            override fun onFailure(call: Call<PutSolusiResponse>, t: Throwable) {
//                Log.d("form", "onFailure: " + t.message)
//                btn_submit.isEnabled = true
//            }
//        })

    }

    private fun convertFile(selectedFile: Uri): File {
        val contentResolver: ContentResolver = this.contentResolver
        val type = (contentResolver.getType(selectedFile) ?: "").split("/")
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val fileNameFormat = "dd-MM-yyyy"
        val timestamp: String =
            SimpleDateFormat(fileNameFormat, Locale.getDefault()).format(System.currentTimeMillis())
        val myFile = File.createTempFile(timestamp, ".${type.last()}")
        val inputStream = contentResolver.openInputStream(selectedFile) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buff = ByteArray(1024)
        var length: Int
        while (inputStream.read(buff).also { length = it } > 0)
            outputStream.write(buff, 0, length)
        inputStream.close()
        outputStream.close()
        return myFile
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, HomepagePrakom::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}