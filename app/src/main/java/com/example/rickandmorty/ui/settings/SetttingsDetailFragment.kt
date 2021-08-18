package com.example.rickandmorty.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSetttingsDetailBinding

class SetttingsDetailFragment : Fragment(R.layout.fragment_setttings_detail) {
    private lateinit var binding: FragmentSetttingsDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetttingsDetailBinding.bind(view)

        binding.tvDetail.text = arguments?.getString("editTextValue") ?: "no value"

    }
}