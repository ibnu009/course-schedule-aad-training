package com.dicoding.courseschedule.ui.setting

import android.app.UiModeManager.*
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NOTIFICATION_CHANNEL_NAME

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference (DONE)
        val prefTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        prefTheme?.setOnPreferenceChangeListener { preference, newValue ->
            Log.d("SettingFragment", "new Value is $newValue and preference is $preference")
            when {
                newValue.equals("on") -> updateTheme(MODE_NIGHT_YES)
                newValue.equals("off") -> updateTheme(MODE_NIGHT_NO)
                else -> updateTheme(MODE_NIGHT_AUTO)
            }
            true
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference (DONE)
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
            Log.d("SettingActivity", "new Value is $newValue and preference is $preference")
            if (newValue == true){
                context?.let { DailyReminder().setDailyReminder(it) }
            } else {
                context?.let { DailyReminder().cancelAlarm(it) }
            }
            true
        }
    }


    private fun updateTheme(nightMode: Int): Boolean {
        Log.d("SettingFragment", "NightMode is $nightMode")

        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }


}