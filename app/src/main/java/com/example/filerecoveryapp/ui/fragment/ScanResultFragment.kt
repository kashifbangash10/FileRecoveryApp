package com.example.filerecoveryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filerecoveryapp.databinding.FragmentScanResultBinding
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderAdapter
import com.example.filerecoveryapp.viewmodel.FileViewModel
import com.example.filerecoveryapp.viewmodel.ScanState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScanResultFragment : Fragment() {

    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FileViewModel by viewModels()
    private lateinit var adapter: FolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView with empty data
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FolderAdapter(mutableListOf()) { folder -> }
        binding.recyclerView.adapter = adapter

        // Observe scanState for updates
        viewModel.scanState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScanState.Scanning -> {
                    binding.scanningtxt.text = "Scanning..." // Display scanning text
                    binding.horizontalProgressBar1.visibility = View.VISIBLE
                    binding.horizontalProgressBar1.progress = state.scanProgress
                    binding.usedStorageText.text = "Scanning: ${state.currentFileName}" // Show the current file being scanned
                }
                is ScanState.Success -> {
                    lifecycleScope.launch {
                        adapter.updateData(state.data) // Update adapter with new data

                        // Once scanning is done, show the folder name or path
                        val folderPath = state.data.firstOrNull()?.name ?: "No media found"
                        binding.usedStorageText.text = folderPath

                        // Hide progress bar when scanning is done
                        binding.horizontalProgressBar1.visibility = View.GONE
                    }
                }
                is ScanState.Error -> {
                    binding.usedStorageText.text = "Error: ${state.message}"
                    binding.horizontalProgressBar1.visibility = View.GONE
                }
                else -> {}
            }
        }

        // Observe progress updates
        viewModel.scanProgress.observe(viewLifecycleOwner) { progress ->
            binding.horizontalProgressBar1.progress = progress
        }

        // Fetch media folders (start scanning)
        viewModel.fetchMediaFolders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
