package com.example.stela_android.Profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stela_android.Login.Login
import com.example.stela_android.R
import com.example.stela_android.Tracking.SistemInformasiActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.popup_logout.*

class PopupLogout :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_logout)
        logout()

    }

    fun logout() {
        btn_logout.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }
    }
}