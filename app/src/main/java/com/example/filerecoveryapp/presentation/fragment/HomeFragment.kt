package com.example.filerecoveryapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.filerecoveryapp.databinding.FragmentHomeBinding
import com.example.filerecoveryapp.domain.model.MediaType
import com.example.filerecoveryapp.presentation.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val mediaViewModel: MediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
        observeStorageInfo()
    }

    private fun setupClickListeners() {
        binding.constraint.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("photo")
            findNavController().navigate(action)
        }

        binding.constraint1.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecoveryVideoFragment("video")
            findNavController().navigate(action)
        }

        binding.constraint2.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecoverPhotoFragment("document")
            findNavController().navigate(action)
        }
    }

    private fun observeStorageInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            mediaViewModel.storageInfo.collect { storageInfo ->
                storageInfo?.let { info ->
                    updateStorageDisplay(info)
                }
            }
        }
    }

    private fun updateStorageDisplay(storageInfo: com.example.filerecoveryapp.domain.repository.StorageInfo) {
        with(binding) {
            val usedGB = formatFileSize(storageInfo.usedSpace)
            val totalGB = formatFileSize(storageInfo.totalSpace)
            
            usedStorageText.text = "Used $usedGB / $totalGB"
            
            val usagePercentage = ((storageInfo.usedSpace.toDouble() / storageInfo.totalSpace.toDouble()) * 100).toInt()
            horizontalProgressBar.progress = usagePercentage
            
            // Update file counts (you can add these TextViews to your layout)
            // photosText.text = "Photos (${storageInfo.photoCount})"
            // videotext.text = "Videos (${storageInfo.videoCount})"
            // other.text = "Documents (${storageInfo.documentCount})"
        }
    }

    private fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        
        return DecimalFormat("#,##0.#").format(
            bytes / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}