package com.example.stela_android.Profile

//import android.R
import android.annotation.SuppressLint
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
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.stela_android.Login.Login
import com.example.stela_android.Petugas.DaftarRating
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
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
        btnListener()
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

                    val nama = prefs?.getString("nama_lengkap", "")
                    val unitKerja = prefs?.getString("unit_kerja", "")
                    val url = prefs?.getString("profile", "https://i.imgur.com/Xlls8fG.png")

                    tv_namaprofil.text = nama
                    tv_unitkerja.text = unitKerja

                    Picasso.get().load(url)
                        .placeholder(R.drawable.circle_1)
                        .transform(CropCircleTransformation())
                        .into(iv_profil)
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

    private fun btnListener(){
        val btn_keluar = requireActivity().findViewById<RelativeLayout>(R.id.rl_keluar_pengguna)
        val btn_data_pengguna = requireActivity().findViewById<RelativeLayout>(R.id.rl_pengguna_user)
        btn_keluar.setOnClickListener {
            showDialog()
        }
        btn_data_pengguna.setOnClickListener{
            val intent = Intent(context, DataPengguna::class.java)
            startActivity(intent)
        }
    }

}

