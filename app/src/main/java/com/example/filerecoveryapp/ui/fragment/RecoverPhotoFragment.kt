package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.filerecoveryapp.databinding.FragmentRecoverPhotoBinding

class RecoverPhotoFragment : Fragment() {

    private var _binding: FragmentRecoverPhotoBinding? = null
    private val binding get() = _binding!!

    private val args: RecoverPhotoFragmentArgs by navArgs() // Retrieve arguments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottie2.setOnClickListener {
            val action = RecoverPhotoFragmentDirections.actionRecoverPhotoFragmentToScanResultFragment2()
            findNavController().navigate(action)
        }

        // Retrieve the argument (recovery type)
        val recoveryType = args.recoveryType
        startScanning(recoveryType)  // Start scanning based on the recovery type
    }

    private fun startScanning(type: String) {
        // Update UI with scanning progress
        binding.onbordtitle.text = "Scanning for $type files..." // Assuming `onbordtitle` is your TextView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up View Binding
    }
}
