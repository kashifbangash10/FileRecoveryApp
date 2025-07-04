package com.example.filerecoveryapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filerecoveryapp.databinding.FragmentScanResultBinding
import com.example.filerecoveryapp.presentation.adapter.MediaFolderAdapter
import com.example.filerecoveryapp.presentation.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScanResultFragment : Fragment() {

    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!
    
    private val mediaViewModel: MediaViewModel by viewModels()
    private lateinit var folderAdapter: MediaFolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        folderAdapter = MediaFolderAdapter { folder ->
            val action = ScanResultFragmentDirections
                .actionScanResultFragmentToScanRecoveryFragment(folder.name)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = folderAdapter
        }
    }

    private fun setupClickListeners() {
        binding.backarrowBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            mediaViewModel.uiState.collect { state ->
                when {
                    state.isLoading -> {
                        binding.scanningtxt.text = "Scanning..."
                        binding.horizontalProgressBar1.visibility = View.VISIBLE
                        binding.usedStorageText.text = "Scanning files..."
                    }
                    state.scanComplete -> {
                        binding.scanningtxt.text = "Scan Results"
                        binding.horizontalProgressBar1.visibility = View.GONE
                        binding.usedStorageText.text = "Found ${state.folders.sumOf { it.fileCount }} files in ${state.folders.size} folders"
                        folderAdapter.submitList(state.folders)
                    }
                    state.error != null -> {
                        binding.scanningtxt.text = "Scan Failed"
                        binding.horizontalProgressBar1.visibility = View.GONE
                        binding.usedStorageText.text = "Error: ${state.error}"
                    }
                    else -> {
                        binding.scanningtxt.text = "Ready to Scan"
                        binding.horizontalProgressBar1.visibility = View.GONE
                        binding.usedStorageText.text = "No scan results available"
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mediaViewModel.scanProgress.collect { progress ->
                binding.horizontalProgressBar1.progress = progress
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}