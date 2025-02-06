//package com.example.filerecoveryapp.ui.fragment
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.example.filerecoveryapp.databinding.FragmentRecoveryOtherFileBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class RecoveryOtherFileFragment : Fragment() {
//
//    private var _binding: FragmentRecoveryOtherFileBinding? = null
//    private val binding get() = _binding!!
//
//    private val args: RecoveryOtherFileFragmentArgs by navArgs() // Retrieve arguments if any (e.g., file types)
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentRecoveryOtherFileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Handle UI interactions, e.g., navigating to a scan result screen
//     //   binding.lottie2.setOnClickListener {
//            val action = RecoveryOtherFileFragmentDirections.actionRecoveryOtherFileFragmentToScanResultFragment2()
//            findNavController().navigate(action)
//        }
//
//        // Retrieve the argument (recovery type or file type) and start scanning
//        val recoveryType = args.recoveryType // If needed, you can pass a recovery type here
//        startScanning(recoveryType)  // Start scanning based on the file type (e.g., PDFs, APKs)
//    }
//
//    private fun startScanning(type: String) {
//        // Update UI with scanning progress for other file types
//        binding.onbordtitle.text = "Scanning for $type files..." // Assuming `onbordtitle` is your TextView
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null // Clean up View Binding
//    }
//}
