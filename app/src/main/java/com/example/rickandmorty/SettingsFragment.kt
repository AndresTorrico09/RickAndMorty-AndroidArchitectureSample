package com.example.rickandmorty

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rickandmorty.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        setRadioButtonListener(view)
        setSeekBarListener()
    }

    private fun setRadioButtonListener(view: View) {
        binding.rgTheme.setOnCheckedChangeListener { radioGroup: RadioGroup, optionId: Int ->
            when (optionId) {
                R.id.radio_dark ->
                    Snackbar.make(view, "radio_dark", Snackbar.LENGTH_SHORT).show()
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
            }
        })
    }
}