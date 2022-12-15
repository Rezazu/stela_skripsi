package com.example.stela_android.Profile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


public class Profile : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResult()
        btnKeluarListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    private fun getResult(){
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val retro = Retrofit.getRetroData(token).create(UserApi::class.java)


        val tv_nama_profil : TextView = requireActivity().findViewById(R.id.tv_nama_profil) as TextView
        val tv_departemen_profil : TextView = requireActivity().findViewById(R.id.tv_departemen_profil2) as TextView
        val tv_username : TextView = requireActivity().findViewById(R.id.tv_username2) as TextView
        val tv_email : TextView = requireActivity().findViewById(R.id.tv_email2) as TextView
        val tv_departemen : TextView = requireActivity().findViewById(R.id.tv_departemen2) as TextView
        val tv_bagian : TextView = requireActivity().findViewById(R.id.tv_bagian2) as TextView
        val tv_telepon : TextView = requireActivity().findViewById(R.id.tv_telepon2) as TextView
        val tv_nomorhp : TextView = requireActivity().findViewById(R.id.tv_hp2) as TextView


        retro.getUser().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseData = response.body()?.data
                if(SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                    val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)

                    val nama = prefs?.getString("nama_lengkap", "DefaultValue")
                    val departemen = prefs?.getString("kd_departemen", "DefaultValue")
                    val username = prefs?.getString("username", "Data username")
                    val email = prefs?.getString("email", "Data email")
                    val bagian = prefs?.getString("bagian", "Data bagian")
                    val telepon = prefs?.getString("telepon", "Data telepon")
                    val nomorhp = prefs?.getString("hp", "Data nomorhp")

                    tv_nama_profil.text = nama
                    tv_departemen_profil.text = departemen
//
                    tv_username.text = ": " + username
                    tv_email.text = ": " + email
                    tv_departemen.text = ": " + departemen
                    tv_bagian.text = ": " + bagian
                    tv_telepon.text = ": " + telepon
                    tv_nomorhp.text = ": " + nomorhp
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+t.message)
            }

        })

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