package com.example.stela_android.Homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stela_android.R
import com.example.stela_android.Retrofit.PostResponse
import com.example.stela_android.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_notifications_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsPage : AppCompatActivity() {

    private val list = ArrayList<PostResponse>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_page)

        backBtnListener()
        rvnotification.setHasFixedSize(true)
        rvnotification.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getPosts().enqueue(object: Callback<ArrayList<PostResponse>>{
            override fun onResponse(
                call: Call<ArrayList<PostResponse>>,
                response: Response<ArrayList<PostResponse>>
            ) {
                val responseCode = response.code().toString()
                response.body()?.let{ list.addAll(it)}
                val adapter = PostAdapter(list)
                rvnotification.adapter = adapter

            }

            override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun backBtnListener() {
        back_btn.setOnClickListener {
            startActivity(Intent(this, Homepage::class.java))
        }
    }
}