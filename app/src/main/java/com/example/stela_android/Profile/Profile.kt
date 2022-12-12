package com.example.stela_android.Profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Storage.SharedPrefManager



public class Profile : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//
//        btn_keluar = findViewById(R.id.btn_keluar)
//        btn_keluar.setOnClickListener {
//            showDialog()
//
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnKeluarListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }


    private fun showDialog(){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.popup_logout)
        dialog.show()
    }

    private fun btnKeluarListener(){
       val btn_keluar = requireActivity().findViewById<Button>(R.id.btn_keluar)
        btn_keluar.setOnClickListener {
            getActivity()?.let { it1 -> SharedPrefManager.getInstance(it1.getApplicationContext()).clear() }
            if(!SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                val intent = Intent(activity, Homepage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
        }
    }

}