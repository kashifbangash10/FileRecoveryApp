package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

        binding.constraint1.setOnClickListener {
            // First Constraint Click: Navigate with "photo" recovery type
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("photo")
            findNavController().navigate(action)
        }

        binding.constraint2.setOnClickListener {
            // Second Constraint Click: Navigate with "video" recovery type
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("video")
            findNavController().navigate(action)
        }
        binding.constraint3.setOnClickListener {
            // Second Constraint Click: Navigate with "video" recovery type
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("video")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
