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

        // Navigate with "photo" recovery type when constraint1 is clicked
        binding.constraint.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("photo")
            findNavController().navigate(action)
        }

        // Navigate with "video" recovery type when constraint2 is clicked
        binding.constraint1.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecoveryVideoFragment("video")
            findNavController().navigate(action)
        }

        // Navigate with other recovery type when constraint3 is clicked
        binding.constraint2.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("document")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
