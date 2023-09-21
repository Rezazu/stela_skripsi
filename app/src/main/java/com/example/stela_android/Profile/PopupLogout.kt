package com.example.stela_android.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stela_android.R
import kotlinx.android.synthetic.main.popup_logout.*

class PopupLogout :Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logout()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popup_logout, container,false)
    }

    fun logout() {
        btn_logout.setOnClickListener{

        }
    }
}