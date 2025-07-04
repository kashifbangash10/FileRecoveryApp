package com.example.filerecoveryapp.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.databinding.FragmentRecoverPhotoBinding
import com.example.filerecoveryapp.databinding.ScanCompletedItemDialogBinding
import com.example.filerecoveryapp.domain.model.MediaType
import com.example.filerecoveryapp.presentation.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecoverPhotoFragment : Fragment() {

    private var _binding: FragmentRecoverPhotoBinding? = null
    private val binding get() = _binding!!

    private val args: RecoverPhotoFragmentArgs by navArgs()
    private val mediaViewModel: MediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupUI() {
        val recoveryType = args.recoveryType
        when (recoveryType) {
            "photo" -> {
                binding.recovertxt.text = "Recover Photos"
                binding.onbordtitle.text = "Tap to scan photos"
            }
            "video" -> {
                binding.recovertxt.text = "Recover Videos"
                binding.onbordtitle.text = "Tap to scan videos"
            }
            "document" -> {
                binding.recovertxt.text = "Recover Documents"
                binding.onbordtitle.text = "Tap to scan documents"
            }
        }
    }

    private fun setupClickListeners() {
        binding.lottie2.setOnClickListener {
            startScanning()
        }

        binding.backarrowBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            mediaViewModel.uiState.collect { state ->
                when {
                    state.isLoading -> {
                        binding.lottie2.visibility = View.VISIBLE
                        binding.onbordtitle.text = "Scanning..."
                    }
                    state.scanComplete -> {
                        binding.lottie2.visibility = View.GONE
                        binding.onbordtitle.text = "Scan Completed!"
                        showScanCompleteDialog(state.folders.sumOf { it.fileCount })
                    }
                    state.error != null -> {
                        binding.lottie2.visibility = View.GONE
                        binding.onbordtitle.text = "Scan Failed"
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                        mediaViewModel.clearError()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mediaViewModel.scanProgress.collect { progress ->
                // You can add a progress bar here if needed
            }
        }
    }

    private fun startScanning() {
        val mediaType = when (args.recoveryType) {
            "photo" -> MediaType.IMAGE
            "video" -> MediaType.VIDEO
            "document" -> MediaType.DOCUMENT
            else -> MediaType.IMAGE
        }
        
        mediaViewModel.scanMedia(mediaType)
    }

    private fun showScanCompleteDialog(fileCount: Int) {
        val dialogBinding = ScanCompletedItemDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        alertDialog.window?.setGravity(Gravity.CENTER)

        dialogBinding.scancomplete.text = "Scan completed successfully!"
        dialogBinding.scancompletetitle.text = "Found $fileCount files for recovery"

        dialogBinding.btnOkey.setOnClickListener {
            alertDialog.dismiss()
            if (findNavController().currentDestination?.id == R.id.recoverPhotoFragment) {
                findNavController().navigate(R.id.action_recoverPhotoFragment_to_scanResultFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}