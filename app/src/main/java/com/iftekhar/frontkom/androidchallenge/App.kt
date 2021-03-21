package com.iftekhar.frontkom.androidchallenge

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.iftekhar.frontkom.androidchallenge.local.MyPrefs

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val value = MyPrefs.theme
        if (value.isNotEmpty()) {
            val currentTheme = AppTheme.valueOf(value)
            applyTheme(currentTheme)
        }


    }

    fun applyTheme(appTheme: AppTheme) {
        if (appTheme == AppTheme.DEFAULT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else if (appTheme == AppTheme.DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}

enum class AppTheme {
    DEFAULT,
    LIGHT,
    DARK

}