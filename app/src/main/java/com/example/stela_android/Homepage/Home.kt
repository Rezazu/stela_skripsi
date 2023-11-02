package com.example.stela_android.Homepage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
// import androidx.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stela_android.Homepage.Notification.NotificationsPage
import com.example.stela_android.Login.Login
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Stela.StelaPage
import com.example.stela_android.Storage.SharedPrefManager
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            btnNotificationListener()
            btnStelaListener()
            getData()
        },2000)
    }

    private fun getData(){
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = token?.let { Retrofit.getRetroData(it).create(UserApi::class.java) }
        retro?.getUser()?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    if(activity?.let { SharedPrefManager.getInstance(it).isLoggedIn } == true){
                        val name = prefs.getString("nama_lengkap", "")
                        tv_name.text = name.toString()
                        val dept = prefs.getString("unit_kerja", "")
                        tv_dept.text = dept.toString()
                        val url = prefs.getString("profile", "https://i.imgur.com/Xlls8fG.png")
                        val photo : ImageView = activity?.findViewById(R.id.iv_photo_pengguna) as ImageView
                        Picasso.get().load(url)
                            .placeholder(R.drawable.circle_1)
                            .transform(CropCircleTransformation())
                            .into(iv_photo_pengguna)
                    }
                } else if (!response.isSuccessful) {
                    getActivity()?.let { it1 -> SharedPrefManager.getInstance(it1.getApplicationContext()).clear() }
                    val intent = Intent(context, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
                progressBar?.visibility = View.GONE
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+ t.message)
                getActivity()?.let { it1 -> SharedPrefManager.getInstance(it1.getApplicationContext()).clear() }
                val intent = Intent(context, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })

    }

    private fun btnNotificationListener() {
        val btnNotif = view?.findViewById<ImageButton>(R.id.btn_notification)
        btnNotif?.setOnClickListener{
            startActivity(Intent(activity, NotificationsPage::class.java))
        }
    }

    private fun btnStelaListener(){
        val btnStela = view?.findViewById<ImageButton>(R.id.btn_stela)
        val stelapage = StelaPage()
        btnStela?.setOnClickListener{
            setCurrentFragment(stelapage)
        }
    }

    fun setCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }
}
