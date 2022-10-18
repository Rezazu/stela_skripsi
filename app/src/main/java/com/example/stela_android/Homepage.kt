package com.example.stela_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        ticketActiveBtnHandler()
    }

    fun ticketActiveBtnHandler() {
        btn_ticket.setOnClickListener {
            startActivity(Intent(this, ActiveTicketPage::class.java))
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.itemIconTintList = null

        val profile = Profile()
        val home = Home()

        setCurrentFragment(home)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btn_profile -> setCurrentFragment(profile)
                R.id.btn_home -> setCurrentFragment(home)
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

