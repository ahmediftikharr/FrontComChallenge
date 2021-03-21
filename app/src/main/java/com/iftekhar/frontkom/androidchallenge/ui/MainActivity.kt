package com.iftekhar.frontkom.androidchallenge.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.iftekhar.frontkom.androidchallenge.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.navigation_home -> HomeFragment()
                else -> SettingFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
            return@setOnNavigationItemSelectedListener true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()
    }
}