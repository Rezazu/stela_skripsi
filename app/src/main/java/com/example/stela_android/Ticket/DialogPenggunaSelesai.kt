package com.example.stela_android.Ticket

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.Petugas.PostPenggunaResponse
import com.example.stela_android.Retrofit.Ticket.TiketApi
import kotlinx.android.synthetic.main.notification.*
import kotlinx.android.synthetic.main.popup_laporan_selesai.*
import kotlinx.android.synthetic.main.popup_laporan_selesai.ed_permasalahan_akhir
import kotlinx.android.synthetic.main.popup_pengguna_selesai.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogPenggunaSelesai (context: Context, id_tiket:Int, keterangan:String): Dialog(context) {

    private var idTiket = id_tiket
    private var Keterangan = keterangan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.popup_pengguna_selesai)
        val dismiss = findViewById<Button>(R.id.btn_kembali)
        val selesai = findViewById<Button>(R.id.btn_update_selesai)
        tv_permaslahan_awal_d.text = Keterangan

        dismiss.setOnClickListener {
            dismiss()
        }
        selesai.setOnClickListener {
            updatePenggunaSelesai()
            dismiss()
        }
    }

    private fun updatePenggunaSelesai(){
        val prefs = context.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val permasalahanAkhir = ed_permasalahan_akhir.getText().toString()
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
            .addFormDataPart("id", idTiket.toString())
            .addFormDataPart("permasalahan_akhir", permasalahanAkhir)
        val requestBody = builder.build()
        val retro = Retrofit.postRetro(token, requestBody).create(TiketApi::class.java)
        retro.updateSelesaiPengguna(requestBody).enqueue(object : Callback<PostPenggunaResponse> {
            override fun onResponse(
                call: Call<PostPenggunaResponse>,
                response: Response<PostPenggunaResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("Success", "onUpdate: " + response.body()?.message)
                    dismiss()
                } else {
                    val myToast = Toast.makeText(
                        context,
                        "Pastikan form tidak ada yang kosong",
                        Toast.LENGTH_LONG
                    )
                    myToast.show()
                }
            }
            override fun onFailure(call: Call<PostPenggunaResponse>, t: Throwable) {
                Log.d("Failed", "onFailure: " + t.message)
            }
        })
    }
}