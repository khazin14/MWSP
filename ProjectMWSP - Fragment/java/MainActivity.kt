package com.example.projectmwsp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
//    private lateinit var textToken : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inisialisasi
        bottomNavigation = findViewById(R.id.bottomNav)
        //agar langsung muncul halaman
        replaceFragment(HomeFragment())
        //fungsikan
        bottomNavigation.setOnItemSelectedListener {

            when(it.itemId){
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.settingsFragment -> replaceFragment(SettingsFragment())
                R.id.userFragment -> replaceFragment(userFragment())
                R.id.exploreFragment -> replaceFragment(ExploreFragment())
                else -> {

                }
            }
            true
        }
        //getToken
//        val preferenceShared = getSharedPreferences("GET_TOKEN", Context.MODE_PRIVATE)
//        val getToken = preferenceShared.getString("token")

    }
    private fun replaceFragment( fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}