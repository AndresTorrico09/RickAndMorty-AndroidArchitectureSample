package com.example.rickandmorty

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rickandmorty.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding.rgTheme.setOnCheckedChangeListener { radioGroup: RadioGroup, optionId: Int ->
            when (optionId) {
                R.id.radio_dark ->
                    Snackbar.make(view, "radio_dark", Snackbar.LENGTH_SHORT).show()
                R.id.radio_light ->
                    Toast.makeText(requireContext(), "radio_light", Toast.LENGTH_SHORT).show()
            }
        }
    }
}