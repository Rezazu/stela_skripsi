package com.example.stela_android.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.*
import com.example.stela_android.Storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initAction()
    }

    fun initAction(){
        btn_masuk.setOnClickListener{
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()
//            if(username.isEmpty() || password.isEmpty() || username.isEmpty() && password.isEmpty()){
//                val myToast =
//                    Toast.makeText(applicationContext, "Username atau Password tidak boleh kosong", Toast.LENGTH_LONG)
//                myToast.show()
//            } else {
//                login()
//            }
        }
    }

    fun login(){
        val retro = Retrofit.getRetroLogin().create(UserApi::class.java)
        val username = et_username.text.toString().trim()
        val password = et_password.text.toString().trim()
        retro.login(username, password).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful()) {
                    val result = response.body()
                    if (result != null) {
                        Log.d("test", result.toString())
                        SharedPrefManager.getInstance(applicationContext).saveUser(result.data?.user!!)
                        SharedPrefManager.getInstance(applicationContext).saveToken(result.data?.token)
                        val myToast = Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_LONG)
                        myToast.show()

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        val myToast =
                            Toast.makeText(applicationContext, "Null", Toast.LENGTH_LONG)
                        myToast.show()
                    }
                }
            }


            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
                val myToast =
                    Toast.makeText(applicationContext, "Login gagal", Toast.LENGTH_LONG)
                myToast.show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, Homepage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}