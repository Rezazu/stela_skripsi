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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.Homepage.Notification.NotificationsPage
import com.example.stela_android.Petugas.Tiket.TiketPetugasFragment
import com.example.stela_android.Retrofit.Petugas.OnTicketPetugasClickListener
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Petugas.*
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Storage.SharedPrefManager
import com.example.stela_android.Ticket.Ticket
import kotlinx.android.synthetic.main.activity_active_ticket_page.*
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
//        getTicketPetugas()
        btnNotificationListener()
        btnTiketAktifListener()
        btnTiketSelesaiListener()
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
                val responseData = response.body()?.data
                val header = responseData?.user
                if(SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                    val nama = prefs?.getString("nama_lengkap", "")
                    tv_name.text = nama
                    val dept = prefs?.getString("bagian", "")
                    tv_dept.text = dept.toString()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+ t.message)
            }

        })
    }

    private fun getTicketPetugas() {
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(PetugasTiketApi::class.java)

        retro.getPermintaan().enqueue(object : Callback<PermintaanResponse> {
            override fun onResponse(
                call: Call<PermintaanResponse>,
                response: Response<PermintaanResponse>
            ) {
                response.body()?.data?.let { list.addAll(it) }

            }

            override fun onFailure(call: Call<PermintaanResponse>, t: Throwable) {
                Log.d("Ticket", "onFailure: " + t.message)
            }
        })
    }

    private fun btnNotificationListener() {
        btn_notification.setOnClickListener{
            startActivity(Intent(activity, NotificationsPage::class.java))
        }
    }

    fun btnTiketAktifListener() {
        btn_aktifP.setOnClickListener {
            btn_aktifP.background = resources.getDrawable(R.drawable.shadow_banner_prakom)
            btn_aktifP.setTextColor(Color.parseColor("#FFFFFF"))

            btn_selesaiP.background = resources.getDrawable(R.drawable.border_blue_prakom)
            btn_selesaiP.setTextColor(Color.parseColor("#000000"))

            ll_selesaiP.visibility = View.GONE

            if(ll_aktifP.visibility == View.GONE) {
                ll_aktifP.visibility = View.VISIBLE
            }
        }
    }
    fun btnTiketSelesaiListener() {
        btn_selesaiP.setOnClickListener {
            btn_selesaiP.background = resources.getDrawable(R.drawable.shadow_banner_prakom)
            btn_selesaiP.setTextColor(Color.parseColor("#FFFFFF"))

            btn_aktifP.background = resources.getDrawable(R.drawable.border_blue_prakom)
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


