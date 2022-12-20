package com.example.stela_android.Form

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Form.FormApi
import com.example.stela_android.Retrofit.Form.PostPermintaanResponse
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_notifications_page.back_btn
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        getDataForm()
        postPermintaan()
        backBtnListener()
        btn_upload.setOnClickListener{
            selectFile()
        }
    }

    var selectedFile = ""
    val filePaths: ArrayList<String> = ArrayList()

    fun createPermintaan() {
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val bagian = ed_bagian.text.toString()
        val gedung = ed_gedung.text.toString()
        val ruangan = ed_ruangan.text.toString()
        val lantai = ed_lantai.text.toString()
        val keterangan = ed_keterangan.text.toString()

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
            .addFormDataPart("bagian", bagian)
            .addFormDataPart("gedung", gedung)
            .addFormDataPart("ruangan", ruangan)
            .addFormDataPart("lantai", lantai)
            .addFormDataPart("keterangan", keterangan)
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
        val retro = Retrofit.postRetro(token, requestBody).create(FormApi::class.java)
        retro.createPermintaan(requestBody).enqueue(object : Callback<PostPermintaanResponse> {
            override fun onResponse(call: Call<PostPermintaanResponse>, response: Response<PostPermintaanResponse>) {
                if(response.isSuccessful){
                    val intent = Intent(applicationContext, FormDone::class.java)
                    val body = response.body()
                    val no_tiket = body?.data?.nomor_tiket
                    intent.putExtra("no_tiket", no_tiket)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
                    startActivity(intent)
                    this@FormActivity.finish()
                }else{
                    val myToast = Toast.makeText(applicationContext, "Pastikan form tidak ada yang kosong", Toast.LENGTH_LONG)
                    myToast.show()
                }
            }

            override fun onFailure(call: Call<PostPermintaanResponse>, t: Throwable) {
                Log.d("form", "onFailure: " + t.message)
            }

        })
    }

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
    private fun postPermintaan(){
        btn_submit.setOnClickListener {
            createPermintaan()
        }
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            val i = Intent(this, Homepage::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }

    private fun selectFile(){
        val intent = Intent()
            .setType("application/pdf")
            .setAction(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        takeFile.launch(intent)
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
                tv_file.setText(nameFile.substring(nameFile.lastIndexOf("/")+1))
            }

        }
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
}
