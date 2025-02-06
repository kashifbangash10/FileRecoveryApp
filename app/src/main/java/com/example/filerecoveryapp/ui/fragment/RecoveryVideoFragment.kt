package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.filerecoveryapp.databinding.FragmentRecoveryVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoveryVideoFragment : Fragment() {

    private var _binding: FragmentRecoveryVideoBinding? = null
    private val binding get() = _binding!!

    private val args: RecoveryVideoFragmentArgs by navArgs() // Retrieve arguments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoveryVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle button click to navigate to ScanResultFragment2
        binding.lottie2.setOnClickListener {
            val action =
                RecoveryVideoFragmentDirections.actionRecoveryVideoFragmentToScanResultFragment2()
            findNavController().navigate(action)
        }

        // Retrieve the argument (recovery type) and start scanning
        val recoveryType = args.recoveryType
        startScanning(recoveryType)
    }

    private fun startScanning(type: String) {
        binding.onbordtitle.text = "Scanning for $type files..."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up View Binding
    }
}
