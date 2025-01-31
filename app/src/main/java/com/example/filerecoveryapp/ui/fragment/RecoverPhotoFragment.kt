package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.filerecoveryapp.databinding.FragmentRecoverPhotoBinding

class RecoverPhotoFragment : Fragment() {

    private var _binding: FragmentRecoverPhotoBinding? = null
    private val binding get() = _binding!!

    private val args: RecoverPhotoFragmentArgs by navArgs() // Use navArgs to retrieve arguments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recoveryType = args.recoveryType// Retrieve the argument
        startScanning(recoveryType)
    }

    private fun startScanning(type: String) {
        // Implement scanning logic here
        // Update UI with progress and results
//        binding.textView. = "Scanning $type..." // Example: Update a TextView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up View Binding
    }
}