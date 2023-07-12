package com.example.stela_android.Homepage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
// import androidx.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.stela_android.Form.FormActivity
import com.example.stela_android.Homepage.Notification.NotificationsPage
import com.example.stela_android.R
import com.example.stela_android.Retrofit.LoginResponse
import com.example.stela_android.Retrofit.Retrofit
import com.example.stela_android.Retrofit.UserApi
import com.example.stela_android.Stela.StelaPage
import com.example.stela_android.Storage.SharedPrefManager
import com.example.stela_android.Tracking.InfrastrukturJaringanActivity
import com.example.stela_android.Tracking.LainnyaActivity
import com.example.stela_android.Tracking.SistemInformasiActivity
import com.example.stela_android.Tracking.TataKelolaActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_notification
import kotlinx.android.synthetic.main.activity_home_2.*
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
        return inflater.inflate(R.layout.activity_home_2, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val btn_permohonan = requireActivity().findViewById<ImageButton>(R.id.btn_permohonan)
//        btn_permohonan.setOnClickListener{
//            val intent_permohonan = Intent(activity, FormActivity::class.java)
//            startActivity(intent_permohonan)
//        }
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
