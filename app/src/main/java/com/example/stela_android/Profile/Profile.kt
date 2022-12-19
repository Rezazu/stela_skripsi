package com.example.stela_android.Profile

//import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.fragment.app.Fragment
import com.example.stela_android.Homepage.Homepage
import com.example.stela_android.Login.Login
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


public open class Profile : Fragment() {
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

        retro.getUser().enqueue(object : Callback<LoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
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
                    tv_username2.text = ":   " + username
                    tv_email2.text = ":   " + email
                    tv_departemen2.text = ":   " + departemen
                    tv_bagian2.text = ":   " + bagian
                    tv_telepon2.text = ":   " + telepon
                    tv_hp2.text = ":   " + nomorhp
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
        val logout = dialog.findViewById<Button>(R.id.btn_logout)
        val dismiss = dialog.findViewById<Button>(R.id.btn_cancel)
        dialog.show()
        logout.setOnClickListener {
            getActivity()?.let { it1 -> SharedPrefManager.getInstance(it1.getApplicationContext()).clear() }
            if(!SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                val intent = Intent(activity, Login::class.java)
                startActivity(intent)
            }
        }
        dismiss.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun btnKeluarListener(){
       val btn_keluar = requireActivity().findViewById<Button>(R.id.btn_keluar)
        btn_keluar.setOnClickListener {
            showDialog()
        }
    }

}

