package com.example.stela_android.Form

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Form.FormApi
import com.example.stela_android.Retrofit.Form.PostResponse
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_notifications_page.back_btn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        getDataForm()
        btn_upload.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "*/*"
            resultLauncher.launch(Intent.createChooser(intent, "Pilih dokumen"))
        }
        backBtnListener()
    }

//    UPLOAD FILE
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri = result.data?.data as Uri
                val file = convertFile(uri)
                documentMultipart(file)
                btn_submit.setOnClickListener {
                    createPermintaan(documentMultipart(file))
                }
            }
        }

    private fun documentMultipart(file: File): MultipartBody.Part {
        val mediaType = "application/pdf".toMediaTypeOrNull()
        return MultipartBody.Part.createFormData(
            "dokumen",
            file.name,
//            file.asRequestBody(mediaType)
        )
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

    private fun createPermintaan(dokumen : MultipartBody.Part) {
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val retro_form = Retrofit.getRetroData(token).create(FormApi::class.java)

        val bagian = ed_bagian.text.toString()
        val gedung = ed_gedung.text.toString()
        val ruangan = ed_ruangan.text.toString()
        val lantai = ed_lantai.text.toString()
        val keterangan = ed_judul.text.toString() + ", " + ed_keterangan.text.toString()

        retro_form.createPermintaan(bagian, gedung, ruangan, lantai, keterangan, arrayOf(dokumen))
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    val intent = Intent(applicationContext, FormDone::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.d("form", "onFailure: " + t.message)
                }

            })
    }
//    UPLOAD FILE
    private fun getDataForm() {
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val retro = Retrofit.getRetroData(token).create(UserApi::class.java)
        retro.getUser().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val nama = prefs.getString("nama_lengkap", " ")
                val unit_kerja = prefs.getString("unit_kerja", " ")
                val hp = prefs.getString("hp", " ")
                val email = prefs.getString("email", " ")
                ed_nama.setText(nama).toString()
                ed_unitkerja.setText(unit_kerja).toString()
                ed_nohp.setText(hp).toString()
                ed_email.setText(email).toString()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("form", "onFailure: " + t.message)
            }

        })
    }


    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }
}
