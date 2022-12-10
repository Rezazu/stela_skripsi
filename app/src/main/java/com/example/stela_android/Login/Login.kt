package com.example.stela_android.Login

import android.app.TaskStackBuilder.create
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Retrofit.UserRequest
import com.example.stela_android.Retrofit.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody.Part.Companion.create
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory.create
import java.lang.ref.Cleaner.create

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initAction()
    }

    fun initAction(){
        btn_masuk.setOnClickListener{
            login()

        }
    }

    fun login(){
        val request = UserRequest()
        request.email = et_username.text.toString().trim()
        request.password = et_password.text.toString().trim()

        val retro = Retrofit().getRetroClientInstance().create(UserApi::class.java)
        retro.login(request).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful()) {
                    val myToast =
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG)
                    myToast.show()

                    val intent = Intent(applicationContext, Homepage::class.java)
                    startActivity(intent)

                }
            }


            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
            }
        })
    }
}