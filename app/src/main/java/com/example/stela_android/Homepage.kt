package com.example.stela_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.itemIconTintList = null

        val profile = Profile()

        bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){
                R.id.btn_profile -> setCurrentFragment(profile)
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