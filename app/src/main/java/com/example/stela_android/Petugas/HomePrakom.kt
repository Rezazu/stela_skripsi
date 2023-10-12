package com.example.stela_android.Petugas

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stela_android.Homepage.Notification.NotificationsPage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Petugas.*
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_home.btn_notification
import kotlinx.android.synthetic.main.activity_home_prakom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePrakom : Fragment() {

    private val list = ArrayList<TiketPetugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home_prakom, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        btnListener()
//        refreshFragment()
        ll_selesaiP.visibility = View.GONE
    }

    private fun getData(){
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(UserApi::class.java)
        val tv_name : TextView = requireActivity().findViewById(R.id.tv_nameP) as TextView
        val tv_dept : TextView = requireActivity().findViewById(R.id.tv_deptP) as TextView
        retro.getUser().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if(SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                    val nama = prefs?.getString("nama_lengkap", "")
                    tv_name.text = nama
                    val dept = prefs?.getString("bagian", "")
                    tv_dept.text = dept.toString()
                    val url = prefs.getString("profile", "https://i.imgur.com/Xlls8fG.png")
                    Picasso.get().load(url)
                        .placeholder(R.drawable.circle_1)
                        .transform(CropCircleTransformation())
                        .into(iv_photo_petugas)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+ t.message)
            }

        })
    }

    private fun btnListener(){
        btn_notification.setOnClickListener{
            startActivity(Intent(activity, NotificationsPage::class.java))
        }
        btn_aktifP.setOnClickListener {
            btn_aktifP.background = resources.getDrawable(R.drawable.shadow_banner_prakom)
            btn_aktifP.setTextColor(Color.parseColor("#FFFFFF"))

            btn_selesaiP.background = resources.getDrawable(R.drawable.border_blue_prakom2)
            btn_selesaiP.setTextColor(Color.parseColor("#000000"))

            ll_selesaiP.visibility = View.GONE

            if(ll_aktifP.visibility == View.GONE) {
                ll_aktifP.visibility = View.VISIBLE
            }
        }
        btn_selesaiP.setOnClickListener {
            btn_selesaiP.background = resources.getDrawable(R.drawable.shadow_banner_prakom)
            btn_selesaiP.setTextColor(Color.parseColor("#FFFFFF"))

            btn_aktifP.background = resources.getDrawable(R.drawable.border_blue_prakom2)
            btn_aktifP.setTextColor(Color.parseColor("#000000"))

            ll_selesaiP.visibility = View.VISIBLE
            ll_aktifP.visibility = View.GONE
        }
    }

//    fun refreshFragment(){
//        swipeToRefrestTiket.setOnRefreshListener {
//            parentFragmentManager.beginTransaction().detach(this).commit()
//            parentFragmentManager.beginTransaction().attach(this).commit()
//            Toast.makeText(context,"Page Refreshed!", Toast.LENGTH_SHORT).show()
//            swipeToRefrestTiket.isRefreshing = false
//        }
//    }

}


