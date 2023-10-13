package com.example.stela_android.Homepage

import android.content.Context
import android.content.Intent
import android.os.Bundle
// import androidx.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.android.synthetic.main.activity_home.btn_notification
import kotlinx.android.synthetic.main.activity_home_2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : Fragment(){
    private lateinit var  imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home_2, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNotificationListener()
        btnStelaListener()
        getData()

    }

    private fun getData(){
        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "")
        val retro = Retrofit.getRetroData(token!!).create(UserApi::class.java)
        val tv_name : TextView = requireActivity().findViewById(R.id.tv_name) as TextView
        val tv_dept : TextView = requireActivity().findViewById(R.id.tv_dept) as TextView
        retro.getUser().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(SharedPrefManager.getInstance(requireActivity()).isLoggedIn){
                    val name = prefs.getString("nama_lengkap", "")
                    tv_name.text = name.toString()
                    val dept = prefs.getString("unit_kerja", "")
                    tv_dept.text = dept.toString()
                    val url = prefs.getString("profile", "https://i.imgur.com/Xlls8fG.png")
                    val photo : ImageView = requireActivity().findViewById(R.id.iv_photo_pengguna) as ImageView
                    Picasso.get().load(url)
                        .placeholder(R.drawable.circle_1)
                        .transform(CropCircleTransformation())
                        .into(iv_photo_pengguna)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Home", "onFailure: "+ t.message)
                if(!SharedPrefManager.getInstance(requireActivity()).isLoggedIn) {
                    val intent = Intent(context, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

            }
        })

    }

    private fun btnNotificationListener() {
        btn_notification.setOnClickListener{
            startActivity(Intent(activity, NotificationsPage::class.java))
        }
    }

    private fun btnStelaListener(){
        val stelapage = StelaPage()
        btn_stela.setOnClickListener{
            setCurrentFragment(stelapage)
        }
    }

    fun setCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }
}
