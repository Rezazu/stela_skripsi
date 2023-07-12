package com.example.stela_android.Petugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.stela_android.Login.Login
import com.example.stela_android.Profile.Profile
import com.example.stela_android.R
import com.example.stela_android.Storage.SharedPrefManager
import com.example.stela_android.Ticket.ActiveTicketPage
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepagePrakom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage_prakom)
        ticketActiveBtnHandler()
    }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    fun ticketActiveBtnHandler() {
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.itemIconTintList = null

        val profile = Profile()
        val home = HomePrakom()
        val activeTicket = ActiveTicketPage()

        setCurrentFragment(home)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btn_profile -> setCurrentFragment(profile)
                R.id.btn_home -> setCurrentFragment(home)
//                R.id.btn_ticket -> setCurrentFragment(activeTicket)
            }
            true
        }
    }

    fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}