package com.example.rickandmorty.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        setPreferences()
        setRadioButtonListener(view)
        setSeekBarListener()
        setButtonListener()
    }

    private fun setButtonListener() {
        binding.btnNext.setOnClickListener {
            val editTextValue = binding.editTextSettings.text.toString()
            val bundle = bundleOf("editTextValue" to editTextValue)

            findNavController().navigate(
                R.id.action_settingsFragmentDest_to_setttingsDetailFragment,
                bundle
            )
        }
    }

    private fun setPreferences() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        binding.sbBrightness.progress = sharedPref.getInt(BRIGHTNESS_PREF, 0)
    }

    private fun setRadioButtonListener(view: View) {
        binding.rgTheme.setOnCheckedChangeListener { radioGroup: RadioGroup, optionId: Int ->
            when (optionId) {
                R.id.radio_dark -> {
                    Snackbar.make(view, "radio_dark", Snackbar.LENGTH_SHORT).show()
                    view.announceForAccessibility("Se activó el Modo Oscuro")
                }
                R.id.radio_light ->
                    Toast.makeText(requireContext(), "radio_light", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSeekBarListener() {
        binding.sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //("write custom code for progress is changed")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //("write custom code for progress is started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // ("write custom code for progress is stopped")
                Toast.makeText(requireContext(), "${seekBar.progress} %", Toast.LENGTH_SHORT).show()

                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putInt(BRIGHTNESS_PREF, seekBar.progress)
                    apply()
                }
            }
        })
    }

    companion object {
        const val BRIGHTNESS_PREF = "BRIGHTNESS_PREF"
    }
}