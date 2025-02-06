package com.example.filerecoveryapp.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.FragmentRecoverPhotoBinding
import com.example.filerecoveryapp.databinding.ScanCompletedItemDialogBinding
import com.example.filerecoveryapp.viewmodel.FileViewModel
import com.example.filerecoveryapp.viewmodel.ScanState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverPhotoFragment : Fragment() {

    private var _binding: FragmentRecoverPhotoBinding? = null
    private val binding get() = _binding!!

    private val args: RecoverPhotoFragmentArgs by navArgs() // Retrieve arguments
    private lateinit var fileViewModel: FileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverPhotoBinding.inflate(inflater, container, false)
        fileViewModel = ViewModelProvider(this).get(FileViewModel::class.java) // Initialize ViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe scan state for progress and completion
        fileViewModel.scanState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScanState.Scanning -> {
                    // Show scanning progress
                    binding.lottie2.visibility = View.VISIBLE
                    binding.onbordtitle.text = "Scanning ..."
                }
                is ScanState.Success -> {
                    // Hide progress and show results
                    binding.lottie2.visibility = View.GONE
                    binding.onbordtitle.text = "Scan Completed!"
                    showCustomDialog("Scan completed successfully!")
                }
                is ScanState.Error -> {
                    // Show error message
                    binding.lottie2.visibility = View.GONE
                    binding.onbordtitle.text = "Scan Failed"
                    showCustomDialog("Error: ${state.message}")
                }
                else -> {}
            }
        }

        // Click on the animation to start scanning
        binding.lottie2.setOnClickListener {
            val recoveryType = args.recoveryType
            startScanning(recoveryType)  // Start scanning based on recovery type
        }
    }

    private fun showCustomDialog(message: String) {
        val dialogBinding = ScanCompletedItemDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        alertDialog.window?.apply {
            setGravity(Gravity.END)  // You can customize the gravity as required
        }

        // Set dialog message
        dialogBinding.scancomplete.text = message // Assuming you have a TextView named scancomplete in the layout

        // Dismiss logic
        dialogBinding.btnOkey.setOnClickListener {
            alertDialog.dismiss()
            if (findNavController().currentDestination?.id == R.id.recoverPhotoFragment) {
                findNavController().navigate(R.id.action_recoverPhotoFragment_to_scanResultFragment2)
            }
        }
    }

    private fun startScanning(type: String) {
        // Update UI with scanning progress
        binding.onbordtitle.text = "Scanning for $type files..."
        fileViewModel.fetchMediaFolders() // Call ViewModel to start the scanning
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up View Binding
    }
}
