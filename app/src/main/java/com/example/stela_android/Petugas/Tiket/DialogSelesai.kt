package com.example.stela_android.Petugas.Tiket

import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Petugas.PostSolusiResponse
import com.example.stela_android.Retrofit.Petugas.UpdateSelesaiApi
import com.example.stela_android.Retrofit.Retrofit
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.notification.*
import kotlinx.android.synthetic.main.popup_laporan_selesai.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class DialogSelesai (context: Context, id_tiket:Int, keterangan:String?): Dialog(context) {

    private var idTiket = id_tiket
    private var Keterangan = keterangan
    var selectedFile = ""
    val filePaths: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.popup_laporan_selesai)
        val dismiss = findViewById<Button>(R.id.btn_kembali)
        val selesai = findViewById<Button>(R.id.btn_update_selesai)
        val upload = findViewById<ImageButton>(R.id.btn_upload)
        val tv_keterangan = findViewById<TextView>(R.id.tv_permaslahan_awal)
        tv_keterangan.text = Keterangan


        dismiss.setOnClickListener {
            dismiss()
        }
        selesai.setOnClickListener {
            updateSelesai()
            dismiss()
        }
        upload.setOnClickListener{
//            selectFile()
        }

    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateSelesai(){
        val prefs = context.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val permasalahan_akhir = ed_permasalahan_akhir.getText().toString()
        val solusi = ed_solusi.getText().toString()
        val id_status_tiket = "6"
        val id_status_tiket_internal = "9"

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
            .addFormDataPart("id", idTiket.toString())
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
        val retro = Retrofit.postRetro(token, requestBody).create(UpdateSelesaiApi::class.java)
        retro.updateSolusi(requestBody).enqueue(object : retrofit2.Callback<PostSolusiResponse> {
            override fun onResponse(call: Call<PostSolusiResponse>, response: Response<PostSolusiResponse>){
                if(response.isSuccessful){
                    Log.d("Success", "onUpdate: " + response.body()?.message)
                    dismiss()
                } else {

                }
            }

            override fun onFailure(call: Call<PostSolusiResponse>, t: Throwable) {
                Log.d("form", "onFailure: " + t.message)
//                btn_submit.isEnabled = true
            }
        })

    }

    private fun convertFile(selectedFile: Uri): File {
        val contentResolver: ContentResolver = context.contentResolver
        val type = (contentResolver.getType(selectedFile) ?: "").split("/")
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
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
}