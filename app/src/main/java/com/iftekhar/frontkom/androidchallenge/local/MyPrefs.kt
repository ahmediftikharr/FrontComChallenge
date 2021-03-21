package com.iftekhar.frontkom.androidchallenge.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.iftekhar.frontkom.androidchallenge.App


abstract class BasePrefs(
    context: Context = App.instance.applicationContext,
    val prefs: SharedPreferences = context.getSharedPreferences(
        "app-prefs",
        0
    )
) : SharedPreferences by prefs

object MyPrefs : BasePrefs() {


    var theme: String
        get() = getString("theme", "") ?: ""
        set(value) = edit { putString("theme", value) }
}