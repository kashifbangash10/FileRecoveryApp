package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listeners using View Binding
        binding.constraint.setOnClickListener {

            navigateToScanningScreen("Photos")

        }

        binding.constraint1.setOnClickListener {
            navigateToScanningScreen("Videos")
        }

        binding.constraint2
            .setOnClickListener {
            navigateToScanningScreen("Recover other files")
        }
    }

    private fun navigateToScanningScreen(type: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment(type)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up View Binding
    }
}