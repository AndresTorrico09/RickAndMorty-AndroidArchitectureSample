package com.example.rickandmorty.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreference
import com.example.rickandmorty.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        setListeners()
    }

    private fun setListeners() {
        val themePreference =
            findPreference<SwitchPreference>(getString(R.string.preference_dark_id))

        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            when (newValue) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            true
        }

        val brightnessPreference =
            findPreference<SeekBarPreference>(getString(R.string.preference_brightness_id))

        brightnessPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Toast.makeText(requireContext(), newValue.toString(), Toast.LENGTH_SHORT).show()
            true
        }
    }

}