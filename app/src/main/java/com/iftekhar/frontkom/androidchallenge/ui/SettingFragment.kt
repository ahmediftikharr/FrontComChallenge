package com.iftekhar.frontkom.androidchallenge.ui

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iftekhar.frontkom.androidchallenge.App
import com.iftekhar.frontkom.androidchallenge.AppTheme
import com.iftekhar.frontkom.androidchallenge.R
import com.iftekhar.frontkom.androidchallenge.databinding.FragmentSettingsBinding
import com.iftekhar.frontkom.androidchallenge.local.MyPrefs

class SettingFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        binding.appVersion.text = "App Version:${getAppVersion()}"
        val themeStr = MyPrefs.theme
        if (themeStr.isNotEmpty()) {
            val appTheme = AppTheme.valueOf(themeStr)
            when (appTheme) {
                AppTheme.DARK -> binding.rgTheme.check(R.id.rb_dark)
                AppTheme.LIGHT -> binding.rgTheme.check(R.id.rb_light)
                AppTheme.DEFAULT -> binding.rgTheme.check(R.id.rb_default)
            }
        }

        binding.rgTheme.setOnCheckedChangeListener { group, checkedId ->
            changeTheme(checkedId)
        }
    }

    private fun changeTheme(checkedId: Int) {
        val cTheme = when (checkedId) {
            R.id.rb_dark -> AppTheme.DARK
            R.id.rb_light -> AppTheme.LIGHT
            else -> AppTheme.DEFAULT
        }
        App.instance.applyTheme(cTheme)
    }

    fun getAppVersion(): String? {
        try {
            val pInfo: PackageInfo =
                requireActivity().getPackageManager()
                    .getPackageInfo(requireActivity().packageName, 0)
            val version = pInfo.versionName
            return version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return "0"
        }
    }
}
