package com.example.stela_android.Profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_petugas.*
import kotlinx.android.synthetic.main.activity_profile_petugas.tv_unitkerja
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataPengguna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pengguna)
        getResult()
    }

    private fun getResult(){
        val prefs = this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val retro = Retrofit.getRetroData(token).create(UserApi::class.java)

        retro.getUser().enqueue(object : Callback<LoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    val nama = prefs?.getString("nama_lengkap", "")
                    val departemen = prefs?.getString("kd_departemen", "")
                    val username = prefs?.getString("username", "")
                    val email = prefs?.getString("email", "")
                    val unit_kerja = prefs?.getString("unit_kerja", "")
                    val bagian = prefs?.getString("bagian","")
                    val telepon = prefs?.getString("telepon", "")
                    val nomorhp = prefs?.getString("hp", "")
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+t.message)
            }

        })
    }

}